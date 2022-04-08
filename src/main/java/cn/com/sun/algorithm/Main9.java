package cn.com.sun.algorithm;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @Author : mockingbird
 * @Date : 2022/3/26 15:31
 * @Description :
 */
public class Main9 {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        int maxLength = Integer.MIN_VALUE;
        //设置窗口大小
        for (int length = 1; length <= input.length(); length++) {
            for (int index = 0; index < input.length() - length; index++) {
                String s = input.substring(index, index + length);
                List<String> list = new ArrayList<>();
                for (int i = 0; i < s.length(); i++) {
                    list.add(s.substring(i, i + 1));
                }
                int size = (int) list.stream().distinct().count();
                // 无重复字符
                if (s.length() == size) {
                    maxLength = s.length() > maxLength ? s.length() : maxLength;
                    break;
                }
            }
        }
        System.out.println(maxLength);
    }
}
