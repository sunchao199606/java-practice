package cn.com.sun.algorithm.leetcode;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author : mockingbird
 * @Date : 2022/3/19 18:20
 * @Description : 任务调度器
 */
public class Test621 {
    /*
    给你一个用字符数组tasks 表示的 CPU 需要执行的任务列表。其中每个字母表示一种不同种类的任务。任务可以以任意顺序执行，
    并且每个任务都可以在 1 个单位时间内执行完。在任何一个单位时间，CPU 可以完成一个任务，或者处于待命状态。
    然而，两个 相同种类 的任务之间必须有长度为整数 n 的冷却时间，因此至少有连续 n 个单位时间内 CPU 在执行不同的任务，或者在待命状态。
    你需要计算完成所有任务所需要的最短时间 。

    示例 1：
    输入：tasks = ["A","A","A","B","B","B"], n = 2
    输出：8
    解释：A -> B -> (待命) -> A -> B -> (待命) -> A -> B
         在本示例中，两个相同类型任务之间必须间隔长度为 n = 2 的冷却时间，而执行一个任务只需要一个单位时间，所以中间出现了（待命）状态。

    示例 2：
    输入：tasks = ["A","A","A","B","B","B"], n = 0
    输出：6
    解释：在这种情况下，任何大小为 6 的排列都可以满足要求，因为 n = 0
    ["A","A","A","B","B","B"]
    ["A","B","A","B","A","B"]
    ["B","B","B","A","A","A"]
    ...
    诸如此类
    示例 3：
    输入：tasks = ["A","A","A","A","A","A","B","C","D","E","F","G"], n = 2
    输出：16
    解释：一种可能的解决方案是：
         A -> B -> C -> A -> D -> E -> A -> F -> G -> A -> (待命) -> (待命) -> A -> (待命) -> (待命) -> A

    提示：
    1 <= task.length <= 104
    tasks[i] 是大写英文字母
    n 的取值范围为 [0, 100]
    */
    public static void main(String[] args) {
        char[] tasks = {'A', 'B', 'C', 'A'};
        System.out.println(leastInterval(tasks, 2));
    }


    public static int leastInterval(char[] tasks, int n) {

        //统计各个字母出现的次数
        int[] countArr = new int[26];
        for (char c : tasks)
            countArr[c - 'A']++;
        List<Integer> sortedCount = Arrays.stream(countArr).sorted().boxed().collect(Collectors.toList());
        int leastInterval;
        // 先求出出现次数最多的字母需要消耗多长时间
        int maxCount = sortedCount.remove(25);
        // 计算执行完最大值至少需要多长时间
        int remain = (maxCount - 1) * n;
        leastInterval = remain + maxCount;
        // 遍历
        for (int i = 24; i >= 0; i--) {
            int count = sortedCount.get(i);
            if (count == 0) continue;
            // 查看剩余坑位
            if (count == maxCount) {
                if (remain - (count - 1) >= 0) {
                    remain = remain - (count - 1);
                    leastInterval++;
                } else {
                    leastInterval += count;
                }
            } else {
                // 查看剩余的坑位是否能容纳count
                if (remain >= count)
                    remain = remain - count;
                else {
                    remain = 0;
                    leastInterval += (count - remain);
                }
            }
        }
        return leastInterval;
    }
}
