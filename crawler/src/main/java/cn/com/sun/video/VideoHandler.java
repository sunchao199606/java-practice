package cn.com.sun.video;

import cn.com.sun.crawler.Config;
import cn.com.sun.crawler.entity.Video;
import cn.com.sun.crawler.impl.AbstractVideoCrawler;
import cn.com.sun.crawler.impl.PornyVideoCrawler;
import cn.com.sun.crawler.util.FileAccessManager;
import cn.com.sun.crawler.util.HttpClient;
import cn.com.sun.crawler.util.IOUtil;
import com.google.common.collect.Lists;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.schild.jave.Encoder;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.encode.AudioAttributes;
import ws.schild.jave.encode.EncodingAttributes;
import ws.schild.jave.encode.VideoAttributes;
import ws.schild.jave.info.MultimediaInfo;
import ws.schild.jave.info.VideoSize;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.*;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * @Description : 视频工具类
 * @Author : mockingbird
 * @Date : 2020/11/29 16:16
 */
public class VideoHandler {

    private static final Logger logger = LoggerFactory.getLogger(VideoHandler.class);

    private final Map<String, String> recordMap;

    private final Map<String, List<File>> downloadedMap;

    public VideoHandler() {
        this.recordMap = FileAccessManager.getInstance().read();
        this.downloadedMap = getDownloadedMap();
    }

    /**
     * 获取视频时长
     *
     * @param source
     * @return
     */
    public static int getVideoDuration(File source) {
        MultimediaObject object = new MultimediaObject(source);
        MultimediaInfo info;
        try {
            info = object.getInfo();
            int second = (int) info.getDuration() / 1000;
            return second;
        } catch (EncoderException e) {
            logger.debug("parse error");
            return 0;
        }
    }

    public void storeByAuthor(String authorName) {
        // 1 获取作者全部视频
        List<Video> authorAllVideoList = getAllVideoByAuthor(authorName);
        // 2 获取已下载视频
        List<File> selectedFileList = new ArrayList<>();
        List<String> repeatList = new ArrayList<>();
        List<String> notDownloadList = new ArrayList<>();
        authorAllVideoList.forEach(video -> {
            String videoName = video.getTitle() + Config.EXT;
            if (downloadedMap.keySet().contains(videoName)) {
                List<File> list = downloadedMap.get(videoName);
                if (list.size() == 1) {
                    selectedFileList.addAll(downloadedMap.get(videoName));
                } else if (list.size() > 1) {
                    repeatList.add(videoName);
                    logger.error("repeat file : {}", videoName);
                }
                return;
            } else {
                if (recordMap.keySet().contains(video.getId())) {
                    String info = recordMap.get(video.getId());
                    String originName = info.substring(info.indexOf("|") + 1) + Config.EXT;
                    List<File> list = downloadedMap.get(originName);
                    if (list == null) {
                        logger.error("not exist file: {}", originName);
                        return;
                    }
                    if (list.size() == 1) {
                        selectedFileList.addAll(downloadedMap.get(originName));
                        //logger.error("name changed: origin name:{},new name:{}", originName, videoName);
                    } else if (list.size() > 1) {
                        repeatList.add(videoName);
                        logger.error("repeat file : {}", videoName);
                    }
                    return;
                } else {
                    notDownloadList.add(videoName);
                    logger.error("not download file : {}", videoName);
                }
            }
        });
        // 3 移动视频
//        notDownloadList.forEach(name -> logger.warn("not download file : {}", name));
//        repeatList.forEach(name -> logger.warn("repeat file : {}", name));
        File destDir = new File("F:\\Download\\crawler\\author\\" + authorName);
        selectedFileList.forEach(file -> {
            logger.info("moving file : {}", file.getName());
            File dest = new File(destDir.getAbsolutePath() + File.separator + file.getName());
            if (dest.exists()) {
                logger.info("has store file : {}", dest.getPath());
                return;
            }
            IOUtil.move(file, dest);
            System.out.println(file.getName());
        });
    }


    /**
     * 视频编码
     */
    public static void encode(File source, VideoSize size) {
        String tempPath = source.getParent() + "\\" + source.getName().replace(".mp4", "");
        File temp = new File(tempPath);
        MultimediaObject object = new MultimediaObject(source);

        AudioAttributes audio = new AudioAttributes();
        VideoAttributes video = new VideoAttributes();
        video.setSize(size);
        EncodingAttributes attrs = new EncodingAttributes();
        //attrs.setEncodingThreads()
        attrs.setOutputFormat("mp4");
        attrs.setVideoAttributes(video);
        attrs.setAudioAttributes(audio);
        Encoder encoder = new Encoder();
        try {
            encoder.encode(object, temp, attrs);
        } catch (EncoderException e) {
            e.printStackTrace();
        }
        File target = new File(tempPath + ".mp4");
        if (source.delete())
            temp.renameTo(target);
        else
            logger.error("视频重命名失败");
    }

    public static void main(String[] args) throws EncoderException {
        //VideoHandler.encode();
    }

    public void deleteSmallVideo(int limitSize) {
        File dir = new File("F:\\Download\\crawler");
        List<File> smallFileList = new ArrayList<>();
        listFiles(dir, f -> {
            //&& VideoUtil.getVideoDuration(f) > 90
            if (f.length() < limitSize) smallFileList.add(f);
        });
        Set<String> downloadedFileSet = FileAccessManager.getInstance().read().values().stream().map(key -> key.substring(key.indexOf("|") + 1)).collect(Collectors.toSet());
//                map(key -> key.split("|")[1]).collect(Collectors.toSet());
        //downloadedFileSet.forEach(s -> System.out.println(s));
        List<File> deleteList = new ArrayList<>();

        smallFileList.forEach(file -> System.out.println(file.getName()));
        System.out.println(smallFileList.size());
        smallFileList.forEach(file -> {
            if (!downloadedFileSet.contains(file.getName())) deleteList.add(file);
        });

        deleteList.sort((f1, f2) -> ((Long) f1.lastModified()).compareTo(f2.lastModified()));
        deleteList.forEach(file -> System.out.println(file.getAbsolutePath()));
        System.out.println(deleteList.size());
//        smallFileList.forEach(f -> {
//            f.delete();
//            if (downloadedFileSet.contains(f.getName())) ;
//        });

    }

    public void deleteRepeatVideo() {
        Map<String, List<File>> videoMap = getDownloadedMap();
        //Map<String, List<File>> repeatVideoMap = new HashMap<>();
        videoMap.forEach((fileName, files) -> {
            if (files.size() > 1) {
//                if (files.size() == 2 && durationPredicate.test(files.get(0), files.get(1))) {
//                    repeatVideoMap.put(fileName, files);
//                }
                StringBuilder stringBuilder = new StringBuilder(fileName + ":");
                files.stream().map(file -> file.getAbsolutePath() + "|").forEach(stringBuilder::append);
                System.out.println(stringBuilder);
            }
        });
//        repeatVideoMap.values().stream().forEach(list -> {
//            if (list.get(0).length() < list.get(1).length()) {
//                list.get(0).delete();
//            } else {
//                list.get(1).delete();
//            }
//        });
    }

    private static void listFiles(File dir, Consumer<File> fileConsumer) {
        for (File f : dir.listFiles()) {
            if (f.isFile()) {
                fileConsumer.accept(f);
            } else {
                listFiles(f, fileConsumer);
            }
        }
    }

    private List<Video> getVideos(String url) {
        String htmlString = HttpClient.getHtmlByHttpClient(url);
        AbstractVideoCrawler crawler = new PornyVideoCrawler(null);
        Document document = Jsoup.parse(htmlString);
        return crawler.getVideoBaseInfo(document);
    }

    private List<Video> getAllVideoByAuthor(String authorName) {
        // 转码
        String name = null;
        try {
            name = URLEncoder.encode(authorName, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            logger.error(e.getMessage(), e);
        }
        int pageNum = 1;
        List<Video> allVideoList = new ArrayList<>();
        String url = "";
        List<Video> tempVideoList;
        do {
            url = String.format("https://91porny.com/author?keywords=%s&page=%d", name, pageNum);
            tempVideoList = getVideos(url);
            allVideoList.addAll(tempVideoList);
            pageNum++;
        } while (!tempVideoList.isEmpty());
        return allVideoList;
    }

    private Map<String, List<File>> getDownloadedMap() {
        Map<String, List<File>> videoMap = new HashMap<>();
        //Map<String, List<File>> repeatVideoMap = new HashMap<>();
        Consumer<File> fileConsumer = file -> {
            if (videoMap.get(file.getName()) == null) {
                videoMap.put(file.getName(), Lists.newArrayList(file));
            } else {
                videoMap.get(file.getName()).add(file);
            }
        };
        listFiles(new File(Config.FILE_SAVE_PATH), fileConsumer);
        return videoMap;
    }
}
