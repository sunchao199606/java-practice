package cn.com.sun.algorithm.other;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Author : mockingbird
 * @Date : 2022/3/18 20:27
 * @Description :
 */
public class Main7 {
//    给你一个字符串 s。请你按照单词在 s 中的出现顺序将它们全部竖直返回。
//    单词应该以字符串列表的形式返回，必要时用空格补位，但输出尾部的空格需要删除（不允许尾随空格）。
//    每个单词只能放在一列上，每一列中也只能有一个单词。
//    示例 1：
//    中等
//    输入：s = "HOW ARE YOU"
//    输出：["HAY","ORO","WEU"]
//    解释：每个单词都应该竖直打印。
//            "HAY"
//            "ORO"
//            "WEU"
//    示例 2：
//
//    输入：s = "TO BE OR NOT TO BE"
//    输出：["TBONTB","OEROOE","   T"]
//    解释：题目允许使用空格补位，但不允许输出末尾出现空格。
//            "TBONTB"
//            "OEROOE"
//            "   T"
//    示例 3：
//
//    输入：s = "CONTEST IS COMING"
//    输出：["CIC","OSO","N M","T I","E N","S G","T"]
//
//    提示：
//
//            1 <= s.length <= 200
//    s 仅含大写英文字母。
//    题目数据保证两个单词之间只有一个空格。

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String[] words = scanner.nextLine().split(" ");
        // 最长单词的长度
        int maxWordLength = 0;
        for (String word : words) {
            maxWordLength = word.length() > maxWordLength ? word.length() : maxWordLength;
        }
        // 按照index遍历
        List<String> verticalStringList = new ArrayList<>(maxWordLength);
        for (int index = 0; index < maxWordLength; index++) {
            StringBuilder stringBuilder = new StringBuilder();
            for (String word : words) {
                char c;
                if (index < word.length()) {
                    c = word.charAt(index);
                } else {
                    c = ' ';
                }
                stringBuilder.append(c);
            }
            verticalStringList.add(stringBuilder.toString());
        }
        verticalStringList.stream().map(s -> {
            while (s.endsWith(" ")) {
                s = s.substring(0, s.length() - 2);
            }
            return s;
        }).forEach(System.out::println);
    }
}
