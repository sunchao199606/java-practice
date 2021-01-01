package cn.com.sun.crawler;

import cn.com.sun.crawler.config.CrawlerConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;

public class CrawlerFactory {
    private static final Logger logger = LoggerFactory.getLogger(CrawlerFactory.class);

    public static VideoCrawler newCrawler() {
        Class<VideoCrawler> clazz = CrawlerConfig.crawler;
        VideoCrawler videoCrawler = null;
        try {
            Constructor<VideoCrawler> constructor = clazz.getConstructor();
            videoCrawler = constructor.newInstance();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return videoCrawler;
    }
}
