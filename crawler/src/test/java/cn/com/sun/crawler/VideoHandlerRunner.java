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
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.MultimediaInfo;
import ws.schild.jave.info.VideoSize;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

/**
 * @Description :
 * @Author : mockingbird
 * @Date : 2020/12/5 18:35
 */
public class VideoHandlerRunner {
    private static String dir = "F:\\Download\\crawler\\2020-11\\2020-11-02";

    private VideoHandler handler;

    @BeforeEach
    public void init() {
        handler = new VideoHandler();
    }

    @ParameterizedTest()
    @ValueSource(strings = {
            ".mp4"})
    public void encodeTo640(String name) {
        String dir = "F:\\Download\\crawler\\2020-10\\2020-10-27";
        File file = new File(dir + "\\" + name);
        VideoHandler.encode(file, new VideoSize(640, 360));
    }


    @ParameterizedTest()
    @ValueSource(strings = {".mp4"})
    public void encodeTo202(String name) {
        String dir = "F:\\Download\\crawler\\2020-10\\2020-10-22";
        File file = new File(dir + "\\" + name);
        VideoHandler.encode(file, new VideoSize(202, 360));
    }

    @ParameterizedTest()
    @ValueSource(strings = {".mp4"})
    public void encodeTo360(String name) {
        String dir = "F:\\Download\\crawler\\2020-10\\2020-10-29";
        File file = new File(dir + "\\" + name);
        VideoHandler.encode(file, new VideoSize(360, 360));
    }

    @ParameterizedTest()
    @MethodSource("fileGenerator")
    public void encodeToNhd(File f) throws EncoderException {
        MultimediaInfo info = new MultimediaObject(f).getInfo();
        if (info.getVideo().getSize().asEncoderArgument().
                equals(new VideoSize(540, 360).asEncoderArgument())) {
            VideoHandler.encode(f, VideoSize.nhd);
        }
    }

    @ParameterizedTest()
    @ValueSource(strings = {"Abyss22b"})
    public void runStoreByAuthor(String authorName) {
        handler.storeByAuthor(authorName, false);
    }

    private List<Video> getVideos(String url) {
        String htmlString = HttpClient.getHtmlByHttpClient(url);
        AbstractVideoCrawler crawler = new PornyVideoCrawler(null);
        Document document = Jsoup.parse(htmlString);
        return crawler.getVideoBaseInfo(document);
    }

    public static Stream<File> fileGenerator() {
        String dir = "F:\\Download\\crawler\\2020-10";
        List<File> fileList = new ArrayList<>();
        VideoHandler.listFiles(new File(dir), fileList);
        return fileList.stream();
    }
}
