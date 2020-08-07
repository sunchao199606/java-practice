package cn.com.sunchao.javaApi;

import org.junit.jupiter.api.Test;

/**
 * @Description : 多线程相关测试
 * @Author : Mockingbird
 * @Date : 2020-07-30 12:41
 */
public class ThreadTest {
    @Test
    public void testThreadLocal() {
        Integer threadGlobal = new Integer(10);
        ThreadLocal<Integer> threadLocal = new ThreadLocal<>();
        threadLocal.set(1000);
        Thread subThread = new Thread(() -> {
            System.out.println("Thread Global:" + threadGlobal.intValue());
            System.out.println(threadLocal.get());
        });
        subThread.start();
        System.out.println("Thread Local:" + threadLocal.get());
        Exception exception = new Exception();
        exception.printStackTrace();
    }
}
