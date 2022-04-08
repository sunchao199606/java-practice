package cn.com.sun.algorithm;

import java.util.Scanner;

/**
 * @Author : mockingbird
 * @Date : 2022/3/12 17:38
 * @Description : 最长回文子串
 */
public class Main5 {
    //    给定一个仅包含小写字母的字符串，求它的最长回文子串的长度。
//    所谓回文串，指左右对称的字符串。
//    所谓子串，指一个字符串删掉其部分前缀和后缀（也可以不删）的字符串
//    数据范围：字符串长度1≤s≤350
//    进阶：时间复杂度：O(n)\O(n) ，空间复杂度：O(n)\O(n)
//
//    输入描述：
//    输入一个仅包含小写字母的字符串
//    输出描述：
//    返回最长回文子串的长度
//
//     示例1
//    输入：
//    cdabbacc
//    输出：
//    4
//    说明：
//    abba为最长的回文子串
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        int maxLength = 1;
        for (int l = 2; l <= input.length(); l++) {
            for (int index = 0; index <= input.length() - l; index++) {
                String s = input.substring(index, index + l);
                if (isDuiChen(s)) {
                    maxLength = s.length();
                    break;
                }
            }
        }
        System.out.println(maxLength);
    }


    public static boolean isDuiChen(String s) {
        int length = s.length();
        for (int i = 0; i < length / 2; i++) {
            if (s.charAt(i) != s.charAt(length - i - 1)) {
                return false;
            }
        }
        return true;
        //return s.equals(new StringBuilder(s).reverse().toString());
    }
}
