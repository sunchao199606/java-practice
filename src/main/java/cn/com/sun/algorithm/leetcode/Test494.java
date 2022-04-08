package cn.com.sun.algorithm.leetcode;

import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author : mockingbird
 * @Date : 2022/3/21 13:38
 * @Description : 目标和
 */
public class Test494 {
    //    给你一个整数数组 nums 和一个整数 target 。
//    向数组中的每个整数前添加'+' 或 '-' ，然后串联起所有整数，可以构造一个表达式 ：
//    例如，nums = [2, 1] ，可以在2之前添加 '+' ，在 1 之前添加 '-' ，然后串联起来得到表达式 "+2-1" 。
//    返回可以通过上述方法构造的、运算结果等于target的不同表达式的数目。
//
//    示例 1：
//    输入：nums = [1,1,1,1,1], target = 3
//    输出：5
//    解释：一共有 5 种方法让最终目标和为 3 。
//            -1 + 1 + 1 + 1 + 1 = 3
//            +1 - 1 + 1 + 1 + 1 = 3
//            +1 + 1 - 1 + 1 + 1 = 3
//            +1 + 1 + 1 - 1 + 1 = 3
//            +1 + 1 + 1 + 1 - 1 = 3
//
//    示例 2：
//    输入：nums = [1], target = 1
//    输出：1
//
//    提示：
//    1 <= nums.length <= 20
//    0 <= nums[i] <= 1000
//    0 <= sum(nums[i]) <= 1000
//    -1000 <= target <= 1000
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int target = Integer.parseInt(scanner.nextLine());
        int[] nums = Arrays.stream(scanner.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        System.out.println(findTargetSumWays(nums, target));
    }

    public static int findTargetSumWays(int[] nums, int target) {
        //构造一棵树下一个节点如果是+则往左，如果是-则往右
        AtomicInteger count = new AtomicInteger(0);
        dfs(0, nums, 0, target, count);
        return count.get();
    }

    private static void dfs(int depth, int[] nums, int sum, int target, AtomicInteger count) {
        //已经到了树的最底层
        if (depth == nums.length) {
            if (sum == target) {
                count.incrementAndGet();
            }
            return;
        }
        dfs(depth + 1, nums, sum + nums[depth], target, count);
        dfs(depth + 1, nums, sum - nums[depth], target, count);
    }


    public static int findTargetSumWaysBaoli(int[] nums, int target) {
        int count = 0;
        for (int i = 0; i < Math.pow(2, nums.length); i++) {
            String binary = Integer.toString(i, 2);
            if (binary.length() < nums.length) {
                // 填充
                StringBuilder stringBuilder = new StringBuilder();
                for (int j = 0; j < nums.length - binary.length(); j++) {
                    stringBuilder.append('0');
                }
                binary = stringBuilder.append(binary).toString();
            }
            int sum = 0;
            for (int k = 0; k < binary.length(); k++) {
                if (binary.charAt(k) == '0')
                    sum -= nums[k];
                else
                    sum += nums[k];
            }
            if (sum == target) count++;
        }
        return count;
    }

//    class Solution {
//        int ans = 0;
//        public void dfs(int[] nums, int i, int target, int curVal){
//            if(i == nums.length){
//                // 到叶子节点
//                if(curVal == target) ans++;
//                return;
//            }
//            dfs(nums, i+1, target, curVal+nums[i]);
//            dfs(nums, i+1, target, curVal-nums[i]);
//        }
//        public int findTargetSumWays(int[] nums, int target) {
//            dfs(nums, 0, target, 0);
//            return ans;
//        }
//    }
}
