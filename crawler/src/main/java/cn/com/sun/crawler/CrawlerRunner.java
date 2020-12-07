package cn.com.sun.crawler;

import cn.com.sun.crawler.entity.Video;
import cn.com.sun.crawler.impl.PornyVideoCrawler;
import cn.com.sun.crawler.interfaces.IVideoCrawler;
import cn.com.sun.crawler.util.FileAccessManager;
import cn.com.sun.crawler.util.HttpClient;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Description : 入口
 * @Author : Mockingbird
 * @Date : 2020-08-16 11:33
 */
public class CrawlerRunner {

    private static final Logger logger = LoggerFactory.getLogger(CrawlerRunner.class);

    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR;

    private static FileAccessManager fileAccessManager;
    private static IVideoCrawler<Video> crawler;
    private static List<String> pages;
    private static AtomicInteger failed = new AtomicInteger(0);
    private static AtomicInteger succeeded = new AtomicInteger(0);

    static {
        fileAccessManager = FileAccessManager.getInstance();
        crawler = new PornyVideoCrawler(fileAccessManager);
        pages = Config.PORNY_PAGES;
        AtomicInteger num = new AtomicInteger(0);
        ThreadFactory threadFactory = r -> {
            Thread t = new Thread(r);
            t.setDaemon(true);
            t.setName("download-processor-" + num.incrementAndGet());
            return t;
        };
        // 无限任务队列
        BlockingQueue<Runnable> linkedBlockingQueue = new LinkedBlockingQueue<>(Integer.MAX_VALUE);
        THREAD_POOL_EXECUTOR = new ThreadPoolExecutor(8, 8, 5, TimeUnit.MINUTES,
                linkedBlockingQueue, threadFactory);
    }

    public static void main(String[] args) {
        // 获取视频列表
        List<Video> videoList = parseVideoList(pages);
        // 存储目录信息
        File dir;
        if (pages.stream().allMatch(p -> p.contains("/author"))) {
            dir = new File(Config.AUTHOR_PATH + Config.authorName);
        } else {
            // 创建对应日期的文件夹
            String date = LocalDate.now().format(DateTimeFormatter.ISO_DATE);
            dir = new File(Config.FILE_SAVE_PATH + date);
        }
        // 解析并下载
        parseAndDownloadVideo(videoList, dir);

        // 监测线程池状态
        Thread threadPoolMonitor = new Thread(() -> {
            while (THREAD_POOL_EXECUTOR.getActiveCount() != 0) {
                try {
                    Thread.sleep(15000);
                    logger.info("total task:{}; complete task:{}; succeeded task:{}; failed task:{}; queued task:{}; active thread:{}",
                            THREAD_POOL_EXECUTOR.getTaskCount(), THREAD_POOL_EXECUTOR.getCompletedTaskCount(), succeeded, failed,
                            THREAD_POOL_EXECUTOR.getQueue().size(), THREAD_POOL_EXECUTOR.getActiveCount());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        threadPoolMonitor.setName("ThreadPoolMonitor");
        threadPoolMonitor.start();

        // 备份downloaded文件防丢失
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            copyRecordFile();
        }));
    }

    private static void parseAndDownloadVideo(List<Video> videoList, File dir) {
        for (Video video : videoList) {
            if (crawler.parseVideoDownloadInfo(video)) {
                Runnable downloadTask = () -> {
                    if (HttpClient.downloadVideoToFs(video, dir)) {
                        fileAccessManager.write(video);
                        fileAccessManager.writeAuthor(video);
                        succeeded.incrementAndGet();
                    } else {
                        failed.incrementAndGet();
                    }
                };
                THREAD_POOL_EXECUTOR.submit(downloadTask);
                logger.debug(THREAD_POOL_EXECUTOR.toString());
            }
        }
    }

    private static List<Video> parseVideoList(List<String> pages) {
        List<Video> tempVideoList = new ArrayList<>();
        for (String pageUrl : pages) {
            logger.info("crawler parse page :{} start", pageUrl);
            tempVideoList.addAll(crawler.parseVideoBaseInfo(pageUrl));
            logger.info("crawler parse page :{} end", pageUrl);
        }
        //Collections.sort
        List<Video> videoList = tempVideoList.stream().distinct().sorted(Comparator.comparingInt(Video::getDuration)).collect(Collectors.toList());
        if (videoList.size() > 0) {
            logger.info("video list：↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓");
            videoList.forEach(video -> logger.info(video.toString()));
            logger.info("video list：↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑");
        }
        return videoList;
    }

    private static void copyRecordFile() {
        String downloadedPath = System.getProperty("user.home") + "\\downloaded";
        String authorInfoPath = System.getProperty("user.home") + "\\authorInfo";
        File fromDownloaded = new File(Config.FILE_SAVE_PATH + "downloaded");
        File toDownloaded = new File(downloadedPath);
        File fromAuthorInfo = new File(Config.FILE_SAVE_PATH + "authorInfo");
        File toAuthorInfo = new File(authorInfoPath);
        try {
            Files.copy(fromDownloaded, toDownloaded);
            Files.copy(fromAuthorInfo, toAuthorInfo);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
