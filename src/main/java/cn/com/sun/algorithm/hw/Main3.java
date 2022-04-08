package cn.com.sun.algorithm.hw;

import java.util.Scanner;

/**
 * @Author : mockingbird
 * @Date : 2022/3/15 21:59
 * @Description : IGMP协议最小响应时间计算问题
 */
public class Main3 {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int num = Integer.parseInt(scanner.nextLine());
        String[] inputLines = new String[num];
        for (int i = 0; i < num; i++) {
            inputLines[i] = scanner.nextLine();
        }
        int minResTime = Integer.MAX_VALUE;
        for (int index = 0; index < inputLines.length; index++) {
            String[] datas = inputLines[index].split(" ");
            int startTime = Integer.parseInt(datas[0]);
            int delay = Integer.parseInt(datas[1]);
            if (delay >= 128) {
                String s = Integer.toBinaryString(delay);
                int mant = Integer.parseInt(s.substring(4, 7), 2);
                int exp = Integer.parseInt(s.substring(1, 4), 2);
                delay = 0;
            }
            int time = startTime + delay;
            minResTime = time < minResTime ? time : minResTime;
        }
        System.out.println(minResTime);
    }
}
