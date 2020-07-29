package cn.com.sunchao.concurrent;

import org.junit.jupiter.api.Test;

/**
 * @Description : 多线程相关测试
 * @Author :sunchao
 * @Date : 2020-07-27 23:57
 */
public class ConcurrentTest {
    @Test
    public void testJoin() throws InterruptedException {
        Thread newThread = new Thread(() -> System.out.println("new thread run task"));
        newThread.start();
        newThread.join();
        System.out.println("main thread run");
    }
}
