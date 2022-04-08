package cn.com.sun.algorithm.hw;

import java.util.Scanner;
/**
 * @Author : mockingbird
 * @Date : 2022/3/15 19:57
 * @Description : 变形IP地址转10进制数字问题
 */
public class Main4 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();
        //判断是否合法
        if (!checkInputValid(input)) {
            System.out.println("invalid IP");
            return;
        }
        // 按位数转换为二进制
        String[] blocks = input.split("#");
        // 连接好的二进制字符串
        StringBuilder allBinary = new StringBuilder();
        for (String block : blocks) {
            String singleBinary = Integer.toBinaryString(Integer.parseInt(block));
            //高位补全
            if (singleBinary.length() < 8) {
                StringBuilder zeros = new StringBuilder();
                for (int i = 0; i < 8 - singleBinary.length(); i++) {
                    zeros.append("0");
                }
                singleBinary = zeros + singleBinary;
            }
            allBinary.append(singleBinary);
        }
        // 二进制字符串转十进制
        System.out.println(Integer.parseInt(allBinary.toString(), 2));
    }

    private static boolean checkInputValid(String input) {
        //判断是否合法
        String[] blocks = input.split("#");
        // 查看是否为四节
        if (blocks.length != 4) {
            return false;
        }
        // 查看数字的范围的合法性
//        boolean allMatch;
        try {
//            allMatch = Arrays.stream(blocks).allMatch(block -> {
//                int num = Integer.parseInt(block);
//                if (num >= 0 && num<=128){
//                    return true;
//                }
//                return false;
//            });
            for (int index = 0; index < 4; index++) {
                int num = Integer.parseInt(blocks[index]);
                if (index == 0) {
                    if (num < 1 || num > 128) {
                        return false;
                    }
                }
                if (num < 0 || num > 255) {
                    return false;
                }
            }
        } catch (Exception e) {
            return false;
        }
        //return allMatch;
        return true;
    }
}
