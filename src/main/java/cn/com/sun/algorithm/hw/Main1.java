package cn.com.sun.algorithm.hw;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @Author : mockingbird
 * @Date : 2022/3/15 19:57
 * @Description : 加油站问题，给定加油站个数n,每隔一个或2个加油站就要加油，问最后到加油站一共有多少种解法
 */
public class Main1 {

    private static final Map<Integer, Long> cachedResultMap = new HashMap<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int n = scanner.nextInt();
        if (n <= 0 || n >= 100) {
            System.out.println((long) 0);
        }
        System.out.println(calculate(n));
    }

    private static long calculate(int n) {
        Long result;
        //先从缓存中取
        result = cachedResultMap.get(n);
        if (result != null)
            return result;
        if (n == 1 || n == 2) {
            cachedResultMap.putIfAbsent(n, (long) n);
            return n;
        }
        //递归计算
        result = calculate(n - 1) + calculate(n - 2);
        //缓存结果
        cachedResultMap.putIfAbsent(n, result);
        return result;
    }
}
