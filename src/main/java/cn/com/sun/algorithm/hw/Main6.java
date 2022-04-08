package cn.com.sun.algorithm.hw;

import java.util.Scanner;

public class Main6 {
//    一个字符串，首尾相连，计算出现偶数个’o’的字符串最长的长度
//    例子：
//    alolobo
//    输出：6
//    looxdolx
//    输出：7
//    bcbcbc
//    输出：6
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        // 从index = 0 开始遍历 window从2开始最大到length
        int inputLength = input.length();
        int maxLength = 0;
        for (int windowSize = 2; windowSize <= inputLength; windowSize++) {
            for (int index = 0; index < inputLength; index++) {
                int beginIndex = index;
                int endIndex = index + windowSize;
                String in = "";
                String overflow = "";
                if (endIndex < inputLength) {
                    in = input.substring(beginIndex, endIndex);
                } else {
                    in = input.substring(beginIndex);
                    int overflowLength = index + windowSize - inputLength;
                    overflow = input.substring(0, overflowLength);
                }
                //遍历字符串
                String all = in + overflow;
                int charNum = 0;
                for (char c : all.toCharArray()) {
                    if (c == 'o') {
                        charNum++;
                    }
                }
                if (charNum % 2 == 0) {
                    maxLength = all.length() > maxLength ? all.length() : maxLength;
                }
            }
        }
        System.out.println(maxLength);
    }
}
