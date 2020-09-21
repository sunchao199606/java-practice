package cn.com.sunchao.concurrent;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Description : 多线程相关测试
 * @Author :sunchao
 * @Date : 2020-07-27 23:57
 */
public class ConcurrentTest<T, M> {

    @Test
    public void testJoin() throws InterruptedException {
        Thread newThread = new Thread(() -> System.out.println("new thread run task"));
        newThread.start();
        newThread.join();
        System.out.println("main thread run");
    }

    @Test
    public void testThreadPool() {
        ExecutorService executor = new ThreadPoolExecutor(2, 8, 10, TimeUnit.SECONDS,
            new SynchronousQueue<>(true));
        IntFunction<Runnable> intToRunnable = i -> {
            Runnable r = () -> {
                System.out.println(i);
            };
            return r;
        };
        List<Runnable> taskList =
            IntStream.range(0, 10).mapToObj(intToRunnable).collect(Collectors.toList());
        taskList.forEach(task -> executor.execute(task));
        executor.shutdown();
    }

    /**
     * 线程池打印10000个数字
     */
    @Test
    public void testPrintNum() throws InterruptedException {
        List<Runnable> runnableList = IntStream.range(0, 5).mapToObj(i -> {
            Runnable r = () -> {
                try {
                    Thread.sleep(i * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("sleep " + i);
            };
            return r;
        }).collect(Collectors.toList());
        AtomicInteger num = new AtomicInteger(0);
        ThreadFactory threadFactory = runnable -> {
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            thread.setName("processor-" + num.incrementAndGet());
            System.out.println(thread.getName() + " created");
            return thread;
        };
        ExecutorService executorService = new ThreadPoolExecutor(10, Integer.MAX_VALUE,
            10, TimeUnit.SECONDS, new SynchronousQueue<>(), threadFactory);
        runnableList.forEach(runnable -> executorService.execute(runnable));
        executorService.shutdown();
        while (true) {
            if (executorService.isTerminated()) {
                System.out.println("end");
                break;
            }
            Thread.sleep(100);
        }
    }

    public <T, M> T get(M m) {
        return null;
    }
}
