package cn.com.sun.algorithm.leetcode;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author : mockingbird
 * @Date : 2022/3/20 14:12
 * @Description : 最短无序连续子数组
 */
public class Test581 {
    /*
     给你一个整数数组 nums ，你需要找出一个 连续子数组 ，如果对这个子数组进行升序排序，那么整个数组都会变为升序排序。
     请你找出符合题意的 最短 子数组，并输出它的长度。

     示例 1：
     输入：nums = [2,6,4,8,10,9,15]
     输出：5
     解释：你只需要对 [6, 4, 8, 10, 9] 进行升序排序，那么整个表都会变为升序排序

     示例 2：
     输入：nums = [1,2,3,4]
     输出：0

     示例 3：
     输入：nums = [1]
     输出：0

     提示：
     1 <= nums.length <= 104
     -105 <= nums[i] <= 105

     进阶：你可以设计一个时间复杂度为 O(n) 的解决方案吗？
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] nums = Arrays.stream(scanner.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        System.out.println(findUnsortedSubarray(nums));
    }

    public static int findUnsortedSubarray(int[] nums) {
        // 先排序
        int[] originNums = nums;
        // 排完序之后跟原有的进行比对判断是否移动了
        int[] sortedNums = Arrays.stream(nums).sorted().toArray();
        // 移动标记数组
        boolean[] moveFlags = new boolean[sortedNums.length];
        for (int i = 0; i < sortedNums.length; i++) {
            moveFlags[i] = sortedNums[i] != originNums[i];
        }
        int firstMoveIndex = -1;
        int lastMoveIndex = -1;
        // 找出第一个移动的与最后一个移动的
        int i = 0;
        int j = moveFlags.length - 1;
        while (i <= j) {
            // 判断是否找到
            if (!(firstMoveIndex != -1)) {
                if (moveFlags[i])
                    firstMoveIndex = i;
                // 还是未找到
                if (firstMoveIndex == -1)
                    i++;
            }
            if (!(lastMoveIndex != -1)) {
                if (moveFlags[j])
                    lastMoveIndex = j;
                if (lastMoveIndex == -1)
                    j--;
            }
            //判断是否都找到了 如果都找到了那么跳出循环，防止死循环
            if (firstMoveIndex != -1 && lastMoveIndex != -1) {
                break;
            }
        }
        if (firstMoveIndex == -1 && lastMoveIndex == -1) {
            return 0;
        }
        return lastMoveIndex - firstMoveIndex + 1 > 0 ? lastMoveIndex - firstMoveIndex + 1 : 0;
    }
}
