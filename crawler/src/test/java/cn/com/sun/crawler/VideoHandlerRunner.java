package cn.com.sun.crawler;

import cn.com.sun.crawler.entity.Video;
import cn.com.sun.crawler.impl.AbstractVideoCrawler;
import cn.com.sun.crawler.impl.PornyVideoCrawler;
import cn.com.sun.crawler.util.HttpClient;
import cn.com.sun.video.VideoHandler;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import ws.schild.jave.info.VideoSize;

import java.io.File;
import java.util.List;

/**
 * @Description :
 * @Author : mockingbird
 * @Date : 2020/12/5 18:35
 */
public class VideoHandlerRunner {
    private static String dir = "F:\\Download\\crawler\\2020-11-02";
    private VideoHandler handler;

    @BeforeEach
    public void init() {
        handler = new VideoHandler();
    }

    @ParameterizedTest()
    @ValueSource(strings = {})
    public void testEncode(String name) {
        File file = new File(dir + "\\" + name);
        VideoHandler.encode(file, new VideoSize(202, 360));
    }

    @ParameterizedTest()
    @ValueSource(strings = {"Abyss22b"})
    public void runStoreByAuthor(String authorName) {
        handler.storeByAuthor(authorName);
    }

    private List<Video> getVideos(String url) {
        String htmlString = HttpClient.getHtmlByHttpClient(url);
        AbstractVideoCrawler crawler = new PornyVideoCrawler(null);
        Document document = Jsoup.parse(htmlString);
        return crawler.getVideoBaseInfo(document);
    }
}
