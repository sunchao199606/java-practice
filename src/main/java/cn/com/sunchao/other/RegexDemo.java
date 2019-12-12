package cn.com.sunchao.other;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ：sunchao
 * @date ：Created in 2019/12/12 14:10
 * @description：正则表达式测试
 */
public class RegexDemo {
    public static void main(String[] args) {
        // 字符串全部为0
        Pattern pattern = Pattern.compile("^0+");
        Matcher matcher = pattern.matcher("");
        System.out.println(matcher.find());
    }
}
