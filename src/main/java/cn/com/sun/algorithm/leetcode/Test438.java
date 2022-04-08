package cn.com.sun.algorithm.leetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @Author : mockingbird
 * @Date : 2022/3/21 13:22
 * @Description : 找到字符串中所有字母异位词
 */
public class Test438 {
//    给定两个字符串s和p，找到s中所有p的异位词的子串，返回这些子串的起始索引。不考虑答案输出的顺序。
//    异位词 指由相同字母重排列形成的字符串（包括相同的字符串）。
//
//    示例1:
//    输入: s = "cbaebabacd", p = "abc"
//    输出: [0,6]
//    解释:
//    起始索引等于 0 的子串是 "cba", 它是 "abc" 的异位词。
//    起始索引等于 6 的子串是 "bac", 它是 "abc" 的异位词。
//
//    示例 2:
//    输入: s = "abab", p = "ab"
//    输出: [0,1,2]
//    解释:
//    起始索引等于 0 的子串是 "ab", 它是 "ab" 的异位词。
//    起始索引等于 1 的子串是 "ba", 它是 "ab" 的异位词。
//    起始索引等于 2 的子串是 "ab", 它是 "ab" 的异位词。
//
//    提示:
//    1 <= s.length, p.length <= 3 * 104
//    s和p仅包含小写字母

    public static void main(String[] args) {
        System.out.println(findAnagrams("abab", "ab"));
    }

    public static List<Integer> findAnagrams(String s, String p) {
        List<Integer> indexList = new ArrayList<>();
        char[] pSorted = p.toCharArray();
        Arrays.sort(pSorted);
        int length = p.length();
        for (int i = 0; i <= s.length() - length; i++) {
            char[] sSorted = s.substring(i, i + length).toCharArray();
            Arrays.sort(sSorted);
            if (Arrays.equals(pSorted, sSorted))
                indexList.add(i);
        }
        return indexList;
    }
}
