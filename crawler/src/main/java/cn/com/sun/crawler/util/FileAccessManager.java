package cn.com.sun.crawler.util;

import cn.com.sun.crawler.config.CrawlerConfig;
import cn.com.sun.crawler.entity.Video;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @Description : 文件读写管理器
 * @Author : Mockingbird
 * @Date : 2020-08-16 10:48
 */
public class FileAccessManager {

    private static final Logger logger = LoggerFactory.getLogger(FileAccessManager.class);

    /**
     * 文件访问读写锁
     */
    private static final ReadWriteLock READ_WRITE_LOCK = new ReentrantReadWriteLock();
    private static final Lock READ_LOCK = READ_WRITE_LOCK.readLock();
    private static final Lock WRITE_LOCK = READ_WRITE_LOCK.writeLock();
    private static FileAccessManager instance;
    private File authorFile;
    private File downloadedFile;

    private FileAccessManager(File dir) {
        this.downloadedFile = new File(dir, "downloaded");
        this.authorFile = new File(dir, "authorInfo");
    }

    public static FileAccessManager getInstance() {
        if (instance == null) {
            File dir = new File(CrawlerConfig.FILE_SAVE_PATH);
            instance = new FileAccessManager(dir);
        }
        return instance;
    }

    /**
     * 读取已下载的文件
     *
     * @return
     */
    public Map<String, String> read() {
        Map<String, String> map = new HashMap<>();
        READ_LOCK.lock();
        try {
            //logger.info("{} get readLock", Thread.currentThread().getName());
            try (BufferedReader br = new BufferedReader(new FileReader(downloadedFile))) {
                br.lines().forEach(info -> {
                    String id = "";
                    id = info.substring(0, info.indexOf("|"));
                    map.putIfAbsent(id, info);
                });
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        } finally {
            READ_LOCK.unlock();
            //logger.info("{} release readLock", Thread.currentThread().getName());
        }
        return map;
    }

    /**
     * 记录下载成功的文件
     *
     * @param video
     */
    public void write(Video video) {
        WRITE_LOCK.lock();
        try {
            //logger.info("{} get writeLock", Thread.currentThread().getName());
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(downloadedFile, true))) {
                bw.write(video + "\n");
                bw.flush();
                logger.info("write downloaded file:{}", video.getTitle() + ".mp4");
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        } finally {
            WRITE_LOCK.unlock();
            //logger.info("{} release writeLock", Thread.currentThread().getName());
        }
    }

    /**
     * 是否已下载
     *
     * @param video
     * @return
     */
    public boolean isDownloaded(Video video) {
        Map<String, String> map = read();
        if (map.keySet().contains(video.getId())) {
            return true;
        }
        return false;
    }

    public void writeAuthor(Video video) {
        WRITE_LOCK.lock();
        try {
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(authorFile, true))) {
                bw.write(String.format("%s|%s\n", video, video.getAuthor()));
                bw.flush();
                logger.info("write author file:{}", video.getAuthor());
            } catch (IOException e) {
                logger.error(e.getMessage(), e);
            }
        } finally {
            WRITE_LOCK.unlock();
        }
    }
}
