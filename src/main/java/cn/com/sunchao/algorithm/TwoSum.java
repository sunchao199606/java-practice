package cn.com.sunchao.algorithm;

import java.util.Arrays;

/**
 * @Description : 给定一个整数数组和一个目标值，找出数组中和为目标值的两个数
 * @Author :sunchao
 * @Date: 2020-04-01 10:47
 */
public class TwoSum {
    public static int[] twoSum(int[] input, int target) {
        int[] output = new int[2];
        for (int firstIndex = 0; firstIndex < input.length - 1; firstIndex++) {
            for (int secondIndex = firstIndex + 1; secondIndex < input.length; secondIndex++) {
                if (input[firstIndex] + input[secondIndex] == target) {
                    output[0] = firstIndex;
                    output[1] = secondIndex;
                    return output;
                }
            }
        }
        System.out.println("未找到合适的解");
        return output;
    }

    public static void main(String[] args) {
        int[] input = {7, 11, 4, 5};
        int target = 9;
        Arrays.stream(twoSum(input, target)).forEach(System.out::println);
    }
}
