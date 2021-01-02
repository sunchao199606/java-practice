package cn.com.sun.crawler.impl;

import cn.com.sun.crawler.AbstractVideoCrawler;
import cn.com.sun.crawler.VideoCrawler;
import cn.com.sun.crawler.CrawlerConfig;
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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

public class PornCrawler extends AbstractVideoCrawler {

    private static final Logger logger = LoggerFactory.getLogger(PornyCrawler.class);
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
            // singlePageUrl
            video.setPageUrl(a.attr("href"));
            // coverUrl
            //video.setCoverUrl(a.select("img").first().attr("src"));
            // duration
            String durationStr = a.select(".duration").first().text();
            int minutes = Integer.parseInt(durationStr.split(":")[0]);
            int seconds = Integer.parseInt(durationStr.split(":")[1]);
            //Duration duration = Duration.ofSeconds(minutes * 60 + seconds);
            video.setDuration(minutes * 60 + seconds);
            // title
            video.setTitle(a.select(".video-title").first().text());
            // author
            String ownText = content.ownText();
            String text = ownText.substring(ownText.lastIndexOf(" 前 ") + 3);
            video.setAuthor(text.split(" ")[0]);
            // watchNum
            //video.setWatchNum(Integer.parseInt(text.split(" ")[1].trim()));
            // storeNum
            //video.setStoreNum(Integer.parseInt(text.split(" ")[2].trim()));
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
    public VideoCrawler parseDownloadInfo() {
        for (Video video : videoList) {
            String pageHtml = HttpClient.getHtmlByHttpClient(video.getPageUrl());
            String downloadUrl = "";
            Document document = Jsoup.parse(pageHtml);
            Element source = document.select("#player_one_html5_api source").first();
            if (source != null) {
                downloadUrl = source.attr("src");
                //logger.info("get video {} download url by page：{}", video.getTitle(), downloadUrl);
            }
            // 分享链接里面取
            if ("".equals(downloadUrl)) {
                Element shareLink = document.selectFirst("#linkForm2 #fm-video_link");
                if (shareLink != null) {
                    for (int i = 0; i < 5; i++) {
                        String shareHtml = getBrowser().getHtml(shareLink.text());
                        Element shareDocument = Jsoup.parse(shareHtml);
                        if (shareDocument.selectFirst("source") == null) {
                            continue;
                        } else {
                            downloadUrl = shareDocument.selectFirst("source").attr("src");
                            break;
                        }
                    }
                    //logger.info("get video {} download url by share link：{}", video.getTitle(), downloadUrl);
                }
            }
            if (downloadUrl.isEmpty()) {
                logger.warn("get {} download url failed", video.getTitle());
                continue;
            }
            video.setDownloadUrl(downloadUrl);
            logger.info("get {} download url: {}", video.getTitle(), video.getDownloadUrl());
            downloadList.add(video);
        }
        // 关闭浏览器
        if (CefApp.getState() == CefApp.CefAppState.INITIALIZED) {
            getBrowser().destroy();
        }
        return this;
    }

    private Browser getBrowser() {
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
            browser.loadURL(url);
            CountDownLatch latch = new CountDownLatch(1);
            countDownLatch.set(latch);
            try {
                latch.await(25, TimeUnit.SECONDS);
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
