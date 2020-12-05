package cn.com.sun.video;

import cn.com.sun.crawler.util.FileAccessManager;
import com.google.common.collect.Lists;
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

    /**
     * 视频编码
     */
    public static void encode() {
        File source = new File("F:\\Download\\crawler\\2020-12-05\\露脸94年舞蹈小骚妻，极品颜值身材，足交无套，最后颜射，想操她的留言.mp4");
        String tempPath = source.getParent() + "\\" + source.getName().replace(".mp4", "");
        File temp = new File(tempPath);
        MultimediaObject object = new MultimediaObject(source);

        AudioAttributes audio = new AudioAttributes();
        VideoAttributes video = new VideoAttributes();
        video.setSize(new VideoSize(202, 360));
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
        VideoHandler.encode();
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

    public static void deleteRepeatVideo() {
        File dir = new File("F:\\Download\\crawler");
        Map<String, List<File>> videoMap = new HashMap<>();
        Map<String, List<File>> repeatVideoMap = new HashMap<>();
        Consumer<File> fileConsumer = file -> {
            if (videoMap.get(file.getName()) == null) {
                videoMap.put(file.getName(), Lists.newArrayList(file));
            } else {
                videoMap.get(file.getName()).add(file);
            }
        };
        listFiles(dir, fileConsumer);
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
}
