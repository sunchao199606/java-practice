package cn.com.sunchao.algorithm;


import org.junit.jupiter.api.Test;

/**
 * @Description : 算法结果测试
 * @Author :sunchao
 * @Date: 2020-04-01 15:32
 */
public class AlgorithmTest {

    @Test
    public void testFibonacci() {
        Fibonacci fibonacci = new Fibonacci();
        System.out.println(fibonacci.fibonacci(6));
    }
}
