package cn.com.sun.algorithm.leetcode;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author : mockingbird
 * @Date : 2022/3/20 15:39
 * @Description : 和为K的子数组
 */
public class Test560 {

    /*
    给你一个整数数组 nums 和一个整数k ，请你统计并返回该数组中和为k的连续子数组的个数。

    示例 1：
    输入：nums = [1,1,1], k = 2
    输出：2

    示例 2：
    输入：nums = [1,2,3], k = 3
    输出：2

    提示：

    1 <= nums.length <= 2 * 104
    -1000 <= nums[i] <= 1000
    -107 <= k <= 107
     */

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int k = Integer.parseInt(scanner.nextLine());
        int[] nums = Arrays.stream(scanner.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        System.out.println(subarraySum(nums, k));
    }

    public static int subarraySum(int[] nums, int k) {
        int count = 0;
        for (int start = 0; start < nums.length; start++) {
            int sum = 0;
            for (int end = start; end < nums.length; end++) {
                sum += nums[end];
                if (sum == k) count++;
            }
        }
        return count;
    }
}
