package cn.com.sun.other;

import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ：sunchao
 * @date ：Created in 2019/12/12 14:10
 * @description：正则表达式测试
 */
public class RegexDemo {
    private static final String REGEX = "/\\..+/";
    private static final Pattern PATTERN = Pattern.compile("\\\\\\..+\\\\");
    public static void main(String[] args) {

        // 字符串全部为0
        File file = new File("D:\\repo\\KunShangNongShang_2021\\ab4x\\AB_Server\\Basic\\cn.com.agree.ab.a4.server\\ROOT\\workspace\\app\\.svn\\ExpandableText.tad");
       System.out.println(PATTERN.matcher(file.getAbsolutePath()).find());
    }
}
