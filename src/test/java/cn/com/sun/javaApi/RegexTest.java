package cn.com.sun.javaApi;

import org.junit.jupiter.api.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Description : 正则测试
 * @Author :sunchao
 * @Date: 2020-07-20 12:57
 */
public class RegexTest {
    @Test
    public void filterChar() {
        String regEx = "[<>/\\|\"*?]";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher("aaf>>><<|||");
        System.out.println(m.replaceAll("").trim());

    }

    @Test
    public void getVideo() {
        String regEx = "(_b_B)([a-z0-9]*)(.mp4)";
        Pattern p = Pattern.compile(regEx);
        Matcher m = p.matcher("https://txmov2.a.yximgs.com/upic/2021/03/10/17/BMjAyMTAzMTAxNzA3MjNfNDQ1Njg1MDkxXzQ1NzQ1NzkyNjUwXzFfMw==_b_B4b3ea0668c0cdf33f883ac3eee34a921.mp4?tag=1-1615456730-xpcwebdetail-0-xw6cvdcqby-144d503564cced86&clientCacheKey=3xuyc7r5khpvnz9_b.mp4&tt=b&di=2747296d&bp=10004");
        if (m.find()){
            System.out.println(m.group(2));
        }
    }

    @Test
    public void replace(){
        String origin = "[原创]继续调教双马尾学妹！";
        System.out.println(origin.replaceAll("\\[原创\\] ?",""));
    }
}
