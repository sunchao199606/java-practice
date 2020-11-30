package cn.com.sun.video;

import com.google.common.collect.Lists;

import java.io.File;
import java.util.*;
import java.util.function.BiPredicate;
import java.util.function.Consumer;

/**
 * @Description : 重复视频查询
 * @Author : mockingbird
 * @Date : 2020/11/29 14:25
 */
public class RepeatVideoFinder {

    public static Map<String, List<File>> videoMap = new HashMap<>();
    public static Map<String, List<File>> repeatVideoMap = new HashMap<>();
    public static Consumer<File> fileConsumer = file -> {
        if (videoMap.get(file.getName()) == null) {
            videoMap.put(file.getName(), Lists.newArrayList(file));
        } else {
            videoMap.get(file.getName()).add(file);
        }
    };
    public static BiPredicate<File, File> durationPredicate =
            (f1, f2) -> VideoUtil.getVideoDuration(f1) == VideoUtil.getVideoDuration(f2);

    public static void main(String[] args) {
        File dir = new File("F:\\Download\\crawler");
        listFiles(dir);
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
        System.out.println("0000000000000000000000000000000000000000000000000");
        repeatVideoMap.forEach((fileName, files) -> {
            StringBuilder stringBuilder = new StringBuilder(fileName + ":");
            files.stream().map(file -> file.getAbsolutePath() + "|").forEach(stringBuilder::append);
            System.out.println(stringBuilder);
        });
        //deleteRepeatVideo();
    }


    private static void deleteRepeatVideo() {
        repeatVideoMap.values().stream().forEach(list -> {
            if (list.get(0).length() < list.get(1).length()) {
                list.get(0).delete();
            } else {
                list.get(1).delete();
            }
        });
    }


    private static void listFiles(File dir) {
        for (File f : dir.listFiles()) {
            if (f.isFile()) {
                fileConsumer.accept(f);
            } else {
                listFiles(f);
            }
        }
    }
}
