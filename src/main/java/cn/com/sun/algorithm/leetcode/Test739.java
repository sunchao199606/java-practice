package cn.com.sun.algorithm.leetcode;

import java.util.Arrays;
import java.util.Scanner;

/**
 * @Author : mockingbird
 * @Date : 2022/3/19 17:28
 * @Description : 每日温度
 */
public class Test739 {
    /*给定一个整数数组temperatures，表示每天的温度，返回一个数组answer，其中answer[i]是指在第i天之后，才会有更高的温度。如果气温在这之后都不会升高，请在该位置用0来代替。

   示例 1:

   输入: temperatures = [73,74,75,71,69,72,76,73]
   输出:[1,1,4,2,1,1,0,0]
   示例 2:

   输入: temperatures = [30,40,50,60]
   输出:[1,1,1,0]
   示例 3:

   输入: temperatures = [30,60,90]
   输出: [1,1,0]

   提示：
      1 <=temperatures.length <= 105
      30 <=temperatures[i]<= 100
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int[] temperatures = Arrays.stream(scanner.nextLine().split(",")).mapToInt(Integer::parseInt).toArray();
        int length = temperatures.length;
        int[] answer = new int[length];
        // 遍历每个温度，填表
        for (int currentIndex = 0; currentIndex < temperatures.length; currentIndex++) {
            // 当前要比较的基准温度
            int currentTemperature = temperatures[currentIndex];
            // 向后遍历温度查看是否大于当前温度
            for (int i = currentIndex + 1; i < temperatures.length; i++) {
                if (temperatures[i] > currentTemperature) {
                    answer[currentIndex] = i - currentIndex;
                    break;
                }
            }
        }
        StringBuilder stringBuilder = new StringBuilder();
        for (int a : answer) {
            stringBuilder.append(a).append(',');
        }
        System.out.println(stringBuilder.substring(0, stringBuilder.length() - 1));
    }
}
