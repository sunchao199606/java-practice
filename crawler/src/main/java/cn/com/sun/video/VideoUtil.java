package cn.com.sun.video;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ws.schild.jave.EncoderException;
import ws.schild.jave.MultimediaObject;
import ws.schild.jave.info.MultimediaInfo;

import java.io.File;

/**
 * @Description : 视频工具类
 * @Author : mockingbird
 * @Date : 2020/11/29 16:16
 */
public class VideoUtil {

    private static final Logger logger = LoggerFactory.getLogger(VideoUtil.class);

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
}
