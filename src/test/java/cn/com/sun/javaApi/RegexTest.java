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

}
