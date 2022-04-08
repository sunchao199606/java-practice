package cn.com.sun.algorithm.type.sw;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author : mockingbird
 * @Date : 2022/4/8 17:23
 * @Description :最大子数组和为k
 */
public class Main10001 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int k = Integer.parseInt(scanner.nextLine());
        int[] arr = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        System.out.println(findMaxK(arr, k));
    }

    private static int findMaxK(int[] arr, int k) {
        Integer max = Integer.MIN_VALUE;
        for (int i = 0; i <= arr.length - k; i++) {
            int sum = 0;
            for (int j = i; j < i + k; j++) {
                sum += arr[j];
            }
            max = sum > max ? sum : max;
        }
        return max;
    }
}
