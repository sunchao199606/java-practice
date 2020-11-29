package cn.com.sun.crawler.impl;

import cn.com.sun.crawler.entity.Video;
import cn.com.sun.crawler.util.FileAccessManager;
import cn.com.sun.crawler.util.HttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description : 91porn.com网站爬取
 * @Author : mockingbird
 * @Date : 2020/9/6 13:53
 */
public class PornVideoCrawler extends AbstractVideoCrawler {

    public PornVideoCrawler(FileAccessManager fileAccessManager) {
        super(fileAccessManager);
    }

    @Override
    protected List<Video> getVideoBaseInfo(Document document) {
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
            video.setWatchNum(Integer.parseInt(text.split(" ")[1].trim()));
            // storeNum
            video.setStoreNum(Integer.parseInt(text.split(" ")[2].trim()));
            videoList.add(video);
        }
        return videoList;
    }

    /**
     * 解析视频下载Url信息
     *
     * @param document
     * @return
     */
    @Override
    protected String getVideoDownloadUrl(Document document) {
        String downloadUrl = "";
        Element source = document.select("#player_one_html5_api source").first();
        if (source != null) {
            downloadUrl = source.attr("src");
            //logger.info("get video {} download url by page：{}", video.getTitle(), downloadUrl);
        }
        // 分享链接里面取
        if ("".equals(downloadUrl)) {
            Element shareLink = document.selectFirst("#linkForm2 #fm-video_link");
            if (shareLink != null) {
                String shareHtml = HttpClient.getHtmlByHttpClient(shareLink.text());
                Element shareDocument = Jsoup.parse(shareHtml);
                downloadUrl = shareDocument.selectFirst("source").attr("src");
                //logger.info("get video {} download url by share link：{}", video.getTitle(), downloadUrl);
            }
        }
        return downloadUrl;
    }
}
