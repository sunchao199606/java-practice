package cn.com.sun.crawler.impl;

import cn.com.sun.crawler.AbstractVideoCrawler;
import cn.com.sun.crawler.CrawlerConfig;
import cn.com.sun.crawler.VideoCrawler;
import cn.com.sun.crawler.entity.Video;
import cn.com.sun.crawler.util.HttpClient;
import cn.com.sun.crawler.util.VideoHandler;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.OS;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefLoadHandlerAdapter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import static org.openqa.selenium.support.ui.ExpectedConditions.attributeToBeNotEmpty;

public class PornCrawler extends AbstractVideoCrawler {

    private static final Logger logger = LoggerFactory.getLogger(PornCrawler.class);
    private static Browser browser;
    private static CountDownLatch browserStarted = new CountDownLatch(1);
    private VideoHandler videoHandler = new VideoHandler();

    @Override
    public List<Video> getVideoBaseInfo(Document document) {
        Elements elements = document.select(".well.well-sm");
        List<Video> videoList = new ArrayList<>();
        for (Element content : elements) {
            Video video = new Video();
            Element a = content.select("a").first();
            // id
            video.setId(a.select("div").first().attr("id"));
            // href
            video.setHref(a.attr("href"));
            // title
            String originTitle = a.select(".video-title").first().text();
            // 去除[原创] 字样
            String title = originTitle.replaceAll("\\[原创\\] ?", "");
            video.setTitle(title);
            videoList.add(video);
        }
        return videoList;
    }

    @Override
    protected Callable<Boolean> createDownloadTask(Video video) {
        if (video.getDownloadUrl().contains(".mp4?")) {
            return () -> HttpClient.downloadVideoToFs(video, CrawlerConfig.workspace);
        } else {
            return () -> videoHandler.downloadFromM3U8(video, CrawlerConfig.workspace);
        }
    }

    @Override
    public VideoCrawler parseVideoExtInfo() {
        CrawlerConfig.stage = "parseVideoExtInfo";
        for (Video video : videoList) {
            String pageHtml = HttpClient.getHtmlByHttpClient(video.getHref());
            Document document = Jsoup.parse(pageHtml);
            if (document.selectFirst(".boxPart") == null) {
                logger.error("parse {} ext info failed", video);
                continue;
            }
            Elements infos = document.selectFirst(".boxPart").select(".info");
            for (int index = 0; index < infos.size(); index++) {
                Element info = infos.get(index);
                // duration
                if (index == 0) {
                    String durationStr = info.child(0).text().trim();
                    int minutes = Integer.parseInt(durationStr.split(":")[0]);
                    int seconds = Integer.parseInt(durationStr.split(":")[1]);
                    video.setDuration(minutes * 60 + seconds);
                } else if (index == 1) {
                    // watchNum
                    video.setWatchNum(Integer.parseInt(info.child(0).text().trim()));
                } else if (index == 3) {
                    // storeNum
                    video.setStoreNum(Integer.parseInt(info.child(0).text().trim()));
                } else continue;
            }
            // author
            video.setAuthor(document.select(".title-yakov").last().selectFirst(".title").text());
            // uid
            String href = document.select(".title-yakov").last().select("a").first().attr("href");
            video.setUid(href.substring(href.indexOf("?UID=") + 5));
            // date
            video.setDate(document.select(".title-yakov").get(0).text());
            // shareUrl
            if (document.selectFirst("#linkForm2 #fm-video_link") == null) {
                logger.info("get video {} shareUrl failed", video);
                continue;
            }
            video.setShareUrl(document.selectFirst("#linkForm2 #fm-video_link").text());
        }
        return this;
    }

    @Override
    public VideoCrawler parseDownloadUrl() {
        // 1.从分享链接里面取 现已失效
        if (videoList.size() > 0) {
            WebDriver driver = getWebDriver();
            WebDriverWait wait = new WebDriverWait(driver, 20);
            for (Video video : videoList) {
                String downloadUrl = "";
                if (!video.getShareUrl().isEmpty()) {
                    downloadUrl = getDownloadUrl(video.getShareUrl(), driver, wait);
                }
                if (downloadUrl.isEmpty()) {
                    logger.warn("get {} download url from share url failed", video.getTitle());
                } else {
                    logger.info("get {} download url from share url: {}", video.getTitle(), video.getShareUrl());
                    video.setDownloadUrl(downloadUrl);
                    downloadList.add(video);
                    continue;
                }
                // page url里面取
                if (!video.getHref().isEmpty()) {
                    downloadUrl = getDownloadUrl(video.getHref(), driver, wait);
                }
                if (downloadUrl.isEmpty()) {
                    logger.warn("get {} download url from page url failed", video.getTitle());
                    logger.warn("get {} download url failed", video.getTitle());
                } else {
                    logger.info("get {} download url from page url: {}", video.getTitle(), video.getHref());
                    video.setDownloadUrl(downloadUrl);
                    downloadList.add(video);
                }
            }
            // 关闭driver
            driver.quit();
        }

        // 过滤下重复的url
        List<Integer> repeatList = new ArrayList<>();
        for (int index = 0; index < downloadList.size(); index++) {
            if (index > 0) {
                Video video = downloadList.get(index);
                List<Video> subList = downloadList.subList(0, index);
                if (subList.stream().map(Video::getDownloadUrl).anyMatch(url -> url.equals(video.getDownloadUrl()))) {
                    logger.warn("delete second video cause url repeat", video.getDownloadUrl());
                    repeatList.add(index);
                }
            }
        }
        repeatList.stream().forEach(index -> downloadList.remove(index.intValue()));
        return this;
    }

    private WebDriver getWebDriver() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-blink-features=AutomationControlled");
        options.addArguments("--headless");
        options.setBinary(new File(CrawlerConfig.BROWSER_PATH));
        // 增加一个 name = "name",value="value" 的 cookie
        ChromeDriver driver = new ChromeDriver(options);
        driver.get(CrawlerConfig.domain);
        WebDriver.Options remoteWebDriverOptions = driver.manage();
        Arrays.stream(CrawlerConfig.COOKIE.split(";")).forEach(c -> {
            String[] entry = c.split("=");
            Cookie cookie = new Cookie(entry[0], entry[1]);
            try {
                remoteWebDriverOptions.addCookie(cookie);
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        });

        return driver;
    }

    private String getDownloadUrl(String url, WebDriver driver, WebDriverWait wait) {
        String downloadUrl;
        try {
            driver.get(url);
            WebElement sourceElement = driver.findElement(By.ByTagName.tagName("source"));
            wait.until(attributeToBeNotEmpty(sourceElement, "src"));
            downloadUrl = sourceElement.getAttribute("src");
        } catch (Exception e) {
            downloadUrl = "";
            logger.warn(e.getMessage());
        }
        return downloadUrl;
    }

    private Browser getCefBrowser() {
        if (browser == null) {
            browser = new Browser();
            try {
                browserStarted.await();
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }
        return browser;
    }

    private class Browser {
        private CefBrowser browser;
        private String currentHtml = "";
        private JFrame jFrame;
        private CefApp cefApp;
        private AtomicReference<CountDownLatch> countDownLatch = new AtomicReference<>();

        private Browser() {
            jFrame = new JFrame();
            cefApp = CefApp.getInstance();
            CefClient cefClient = cefApp.createClient();
            cefClient.addLoadHandler(new CefLoadHandlerAdapter() {
                @Override
                public void onLoadEnd(CefBrowser cefBrowser, CefFrame cefFrame, int i) {
                    cefBrowser.getSource(html -> {
                        if (browserStarted.getCount() > 0) {
                            // 解除等待
                            browserStarted.countDown();
                            // 最小化窗口
                            jFrame.setExtendedState(Frame.ICONIFIED);
                            return;
                        }
                        currentHtml = html;
                        countDownLatch.get().countDown();
                    });
                }
            });
            //
            browser = cefClient.createBrowser("file:///D:/Program/workspace/IDEA/java/JavaDemo/crawler/src/main/resources/index.html", OS.isLinux(), false);
            jFrame.getContentPane().add(browser.getUIComponent(), BorderLayout.CENTER);
            jFrame.pack();
            jFrame.setSize(500, 500);
            jFrame.setResizable(false);
            jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
            jFrame.setVisible(true);
        }

        public String getHtml(String url) {
            // 初始化
            currentHtml = "";
            logger.info("browser loading {}", url);
            browser.loadURL(url);
            CountDownLatch latch = new CountDownLatch(1);
            countDownLatch.set(latch);
            try {
                latch.await(120, TimeUnit.SECONDS);
                logger.info("browser load {} end", url);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
            return currentHtml;
        }

        public void destroy() {
            CefApp.getInstance().dispose();
            jFrame.dispose();
        }
    }
}
