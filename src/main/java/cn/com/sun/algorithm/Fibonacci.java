package cn.com.sun.algorithm;

/**
 * @Description : 斐波那契数列
 * @Author :sunchao
 * @Date: 2020-04-01 15:22
 */
public class Fibonacci {

    public int fibonacci(int n) {
        if (n == 1) {
            return 0;
        }
        if (n == 2) {
            return 1;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
