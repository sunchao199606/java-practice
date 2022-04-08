package cn.com.sun.algorithm.other;

import java.util.*;

/**
 * @Author : mockingbird
 * @Date : 2022/3/12 15:57
 * @Description : Main1
 */
public class Main1 {
    // 从一堆数种取出所有众数，输出中间的那个，如果有偶数个众数，输出中间两个的平均值
//    用例1：
//            2 1 5 4 3 3 9 2 7 4 6 2 15 4 2 4
//            3
//    用例2：
//            5 1 5 3 5 2 5 5 7 6 7 3 7 11 7 55 7 9 98 9 17 9 15 9 9 1 39
//            7
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] numbers = Arrays.stream(scanner.nextLine().split(" ")).mapToInt(Integer::parseInt).toArray();

        HashMap<Integer, Integer> num2CountMap = new HashMap<>();
        for (int number : numbers) {
            //如果没有则放入，如果有则计算
            if (num2CountMap.containsKey(number)) {
                num2CountMap.compute(number, (k, v) -> ++v);
            } else {
                num2CountMap.put(number, 1);
            }
        }
        List<Integer> zhongshuList = new ArrayList<>();
        int max = 0;
        for (Map.Entry<Integer, Integer> entry : num2CountMap.entrySet()) {
            int num = entry.getValue();
            if (num > max) {
                max = num;
                zhongshuList.clear();
                zhongshuList.add(entry.getKey());
            } else if (num == max) {
                zhongshuList.add(entry.getKey());
            }
        }
        zhongshuList.sort(Integer::compareTo);
        if (zhongshuList.size() % 2 == 0) {
            //偶数个众数
            int index = zhongshuList.size() / 2;
            System.out.println((zhongshuList.get(index) + zhongshuList.get(index - 1)) / 2);
        } else {
            //单数个众数
            System.out.println(zhongshuList.get(zhongshuList.size() / 2));
        }
    }
}
