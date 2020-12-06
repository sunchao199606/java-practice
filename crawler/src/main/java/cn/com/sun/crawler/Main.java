package cn.com.sun.crawler;

import cn.com.sun.crawler.entity.Video;
import cn.com.sun.crawler.impl.PornVideoCrawler;
import cn.com.sun.crawler.impl.PornyVideoCrawler;
import cn.com.sun.crawler.interfaces.IVideoCrawler;
import cn.com.sun.crawler.util.FileAccessManager;
import cn.com.sun.crawler.util.HttpClient;
import com.google.common.io.Files;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

/**
 * @Description : 入口
 * @Author : Mockingbird
 * @Date : 2020-08-16 11:33
 */
public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class);

    private static final ThreadPoolExecutor THREAD_POOL_EXECUTOR;

    static {
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
        FileAccessManager fileAccessManager = FileAccessManager.getInstance();
        IVideoCrawler<Video> crawler;
        List<String> pages = null;
        if (Config.SITE.equals("porn")) {
            crawler = new PornVideoCrawler(fileAccessManager);
            pages = null;
            // 走代理
            Config.HTTP_PROXY_PORT = 1080;
        } else {
            crawler = new PornyVideoCrawler(fileAccessManager);
            pages = Config.PORNY_PAGES;
        }
        Set<Video> videoSet = new LinkedHashSet<>();
        for (String pageUrl : pages) {
            logger.info("crawler parse page :{} start", pageUrl);
//            String encodeUrl = "";
//            try {
//                encodeUrl = URLEncoder.encode(pageUrl, "UTF-8");
//            } catch (UnsupportedEncodingException e) {
//                logger.error(e.getMessage(), e);
//            }
            videoSet.addAll(crawler.parseVideoBaseInfo(pageUrl));
            logger.info("crawler parse page :{} end", pageUrl);
        }

        Set<Video> filteredVideoSet =
                videoSet.stream().sorted(Comparator.comparingInt(Video::getDuration)).collect(Collectors.toCollection(LinkedHashSet::new));
        if (filteredVideoSet.size() > 0) {
            logger.info("video list：↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓↓");
            filteredVideoSet.forEach(video -> logger.info(video.toString()));
            logger.info("video list：↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑↑");
        }
        AtomicInteger failed = new AtomicInteger(0);
        AtomicInteger succeeded = new AtomicInteger(0);
        for (Video video : filteredVideoSet) {
            if (crawler.parseVideoDownloadInfo(video)) {
                Runnable downloadTask = () -> {
                    if (HttpClient.downloadVideoToFs(video)) {
                        fileAccessManager.write(video);
                        succeeded.incrementAndGet();
                    } else {
                        failed.incrementAndGet();
                    }
                };
                THREAD_POOL_EXECUTOR.submit(downloadTask);
                logger.debug(THREAD_POOL_EXECUTOR.toString());
            }
        }
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
            copyDownloadedFile();
        }));
    }

    private static void copyDownloadedFile() {
        String path = System.getProperty("user.home") + "\\downloaded";
        File from = new File(Config.DOWNLOADED_FILE_PATH);
        File to = new File(path);
        try {
            Files.copy(from, to);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }
}
