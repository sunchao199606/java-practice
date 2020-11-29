package cn.com.sun.crawler.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @Description : IO 工具类
 * @Author : Mockingbird
 * @Date : 2020-08-16 17:19
 */
public class IOUtil {

    public static void copy(InputStream in, OutputStream out) throws IOException {
        byte[] bytes = new byte[4096];
        int count = 0;
        while ((count = in.read(bytes)) != -1) {
            out.write(bytes, 0, count);
        }
        out.flush();
    }
}
