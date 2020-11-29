package cn.com.sun.crawler.impl;

import cn.com.sun.crawler.entity.Video;
import cn.com.sun.crawler.interfaces.IVideoCrawler;
import cn.com.sun.crawler.util.FileAccessManager;
import cn.com.sun.crawler.util.HttpClient;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Description : 网络视频爬虫实现
 * @Author : Mockingbird
 * @Date : 2020-08-16 10:28
 */
public abstract class AbstractVideoCrawler implements IVideoCrawler<Video> {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractVideoCrawler.class);

    protected FileAccessManager fileAccessManager;

    public AbstractVideoCrawler(FileAccessManager fileAccessManager) {
        this.fileAccessManager = fileAccessManager;
    }

    @Override
    public List<Video> parseVideoBaseInfo(String pageUrl) {
        String htmlString = HttpClient.getHtmlByHttpClient(pageUrl);
        Map<String, String> downloadedMap = fileAccessManager.read();
        Document document = Jsoup.parse(htmlString);
        return getVideoBaseInfo(document).stream().filter(video -> {
            if (downloadedMap.keySet().contains(video.getId())) {
                logger.info("filter downloaded video:{}", video.getTitle());
                return false;
            } else {
                return true;
            }
        }).collect(Collectors.toList());
    }

    @Override
    public boolean parseVideoDownloadInfo(Video video) {
        String htmlString = HttpClient.getHtmlByHttpClient(video.getPageUrl());
        Document document = Jsoup.parse(htmlString);
        String downloadUrl = getVideoDownloadUrl(document);
        if ("".equals(downloadUrl)) {
            logger.warn("get {} download url failed", video.getTitle());
            return false;
        }
        logger.info("get {} download url: {}", video.getTitle(), downloadUrl);
        video.setDownloadUrl(downloadUrl);
        return true;
    }

    /**
     * 爬取页面中全部视频基础信息
     *
     * @param document
     * @return
     */
    protected abstract List<Video> getVideoBaseInfo(Document document);

    /**
     * 解析视频下载Url信息
     *
     * @param html
     * @return
     */
    protected abstract String getVideoDownloadUrl(Document html);
}
