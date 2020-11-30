package cn.com.sun.crawler;

import cn.com.sun.video.VideoUtil;
import org.junit.jupiter.api.Test;

import java.io.File;

/**
 * @Description : 文件大小测试
 * @Author : mockingbird
 * @Date : 2020/11/29 15:56
 */
public class FileSizeTest {
    @Test
    public void getFileSize() {

        File file = new File("F:\\Download\\crawler\\2020-08-06\\喜欢穿丝袜的欲望人妻.mp4");
        //File file = new File("F:\\Download\\crawler\\2020-11-02\\19岁校服白袜play.mp4");
        System.out.println(VideoUtil.getVideoDuration(file));
        System.out.println(file.length());
    }
}
