package cn.com.sun.algorithm.leetcode;

import java.util.Scanner;

/**
 * @Author : mockingbird
 * @Date : 2022/3/21 13:30
 * @Description : 解码字符串
 */
public class Test394 {
//    给定一个经过编码的字符串，返回它解码后的字符串。
//    编码规则为: k[encoded_string]，表示其中方括号内部的 encoded_string 正好重复 k 次。注意 k 保证为正整数。
//    你可以认为输入字符串总是有效的；输入字符串中没有额外的空格，且输入的方括号总是符合格式要求的。
//    此外，你可以认为原始数据不包含数字，所有的数字只表示重复的次数 k ，例如不会出现像3a或2[4]的输入。
//
//    示例 1：
//    输入：s = "3[a]2[bc]"
//    输出："aaabcbc"
//
//    示例 2：
//    输入：s = "3[a2[c]]"
//    输出："accaccacc"
//
//    示例 3：
//    输入：s = "2[abc]3[cd]ef"
//    输出："abcabccdcdcdef"
//    示例 4：
//
//    输入：s = "abc3[cd]xyz"
//    输出："abccdcdcdxyz"
//
//    提示：
//    1 <= s.length <= 30
//    s由小写英文字母、数字和方括号'[]' 组成
//    s保证是一个有效的输入。
//    s中所有整数的取值范围为[1, 300]

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        System.out.println(decodeString(input));
    }

    public static String decodeString(String s) {
        // aaa2[rrr]3[a2[c]]
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            // 英文字母
            char c = s.charAt(i);
            if (Character.isLetter(c)) {
                builder.append(c);
            } else if (Character.isDigit(c)) {
                // 分两种情况不需要递归 需要递归
                if (isNest(s.substring(i + 2))) {
                    int start = s.indexOf("[", i) + 1;
                    int end = s.lastIndexOf("]");
                    String encodeString = s.substring(start, end);
                    String s1 = decodeString(encodeString);
                    for (int count = 0; count < Character.getNumericValue(c); count++) {
                        builder.append(s1);
                    }
                    // 判断i是否到达了边界
                    // 判断是否还有下一个编码单元
                    //if (end == s.length())
                    return builder.toString();
                } else {
                    int start = s.indexOf("[", i) + 1;
                    int end = s.indexOf("]", start);
                    for (int count = 0; count < Character.getNumericValue(c); count++) {
                        builder.append(s, start, end);
                    }
                    i = end;
                }
            }
        }
        return builder.toString();
    }

    /**
     * 是否嵌套
     *
     * @param s
     * @return
     */
    private static boolean isNest(String s) {
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '[') return true;
            if (s.charAt(i) == ']') return false;
        }
        return false;
    }

//    private static boolean hasNext(int currentIndex, String s) {
//        //for ()
//    }

}
