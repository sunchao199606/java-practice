package cn.com.sun.crawler;

import cn.com.sun.crawler.entity.Video;
import cn.com.sun.crawler.impl.PornVideoCrawler;
import cn.com.sun.crawler.interfaces.IVideoCrawler;
import cn.com.sun.crawler.util.FileAccessManager;
import cn.com.sun.crawler.util.HttpClient;
import com.google.common.io.Files;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/**
 * @Description : test
 * @Author : Mockingbird
 * @Date: 2020-07-19 14:17
 */
public class VideoCrawlerTest {

    private static final Logger logger = LoggerFactory.getLogger(VideoCrawlerTest.class);

    @ParameterizedTest()
    @ValueSource(strings = {"http://www.91porn.com/view_video.php?viewkey=d0c8e3017f126335cadb"})
    public void downloadFromViewKey(String url) {
        FileAccessManager fileAccessManager = FileAccessManager.getInstance();
        IVideoCrawler crawler = new PornVideoCrawler(fileAccessManager);
        Video video = new Video();
        video.setPageUrl(url);
        video.setTitle(getVideoTitle(url));
        if (crawler.parseVideoDownloadInfo(video)) {
            String downloadUrl = video.getDownloadUrl();
            String id = "playvthumb_" + downloadUrl.substring(downloadUrl.indexOf(".mp4") - 6, downloadUrl.indexOf(".mp4"));
            video.setId(id);
            if (!fileAccessManager.isDownloaded(video)) {
                if (HttpClient.downloadVideoToFs(video)) fileAccessManager.write(video);
            }
        }

    }

    @Test
    public void testRecoverDownloaded() throws Exception {
        File file = new File("D:\\Program\\workspace\\IDEA\\Crawler\\crawler.log");
        File outFile = new File("D:\\Program\\workspace\\IDEA\\Crawler\\downloaded");
        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(outFile))) {
            bufferedReader.lines().filter(line -> line.contains("playvthumb_")).filter(line -> line.contains("INFO"))
                .map(p -> p.substring(p.indexOf("playvthumb"))).distinct().forEach(line -> {
                try {
                    bufferedWriter.write(line);
                    bufferedWriter.newLine();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            bufferedWriter.flush();
        }
        //List<String> downloaded = new ArrayList<>();
    }

    @Test
    public void test() {
        String path = System.getProperty("user.home") + "\\downloaded";
        File to = new File(path);
        File from = new File(FileAccessManager.class.getResource("downloaded").getPath());
        try {
            Files.copy(from, to);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Test
    public void testDownLoad(){
        Video video = new Video();
        video.setDownloadUrl("https://cfdc.91p52.com//mp43/394100.mp4?st=5-9M6SQwUZ9JSvW2zFRCBg");
        HttpClient.downloadVideoToFs(video);
    }

    @Test
    public void testGetHtml(){
        logger.info(HttpClient.getHtmlByHttpClient("http://91.9p9.xyz/ev.php?VID=48fdRwSk7y21zZjpdgisWqmrWFL2sFkNcE6n6SG8gZHE5PkG"));
    }

    private String getVideoTitle(String url) {
        String html = HttpClient.getHtmlByHttpClient(url);
        Document document = Jsoup.parse(html);
        String title = document.selectFirst("#videodetails").selectFirst(".login_register_header").text();
        return title;
    }
}

