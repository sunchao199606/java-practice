package cn.com.sun.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 当阻塞的线程个数达到指定个数的时候，同时将全部等待线程notifyAll
 */
public class CycleBarrier {

    public static void main(String[] args) {
        CyclicBarrier barrier = new CyclicBarrier(10);
        AtomicInteger id = new AtomicInteger(0);
        for (int i = 0; i < 9; i++) {
            Thread thread = new Thread(() -> {
                try {
                    barrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
                System.out.println("player " + id.getAndIncrement() + " run");
            });
            thread.start();
        }
        try {
            Thread.currentThread().sleep(3000l);
            barrier.await();
        } catch (InterruptedException | BrokenBarrierException e) {
            e.printStackTrace();
        }
        barrier();
    }


    private static void barrier() {
        Object obj = new Object();
        for (int i = 0; i < 10; i++) {
            final int ref = i;
            Thread thread = new Thread(() -> {
                synchronized (obj) {
                    try {
                        System.out.println("Thread " + ref + " waiting");
                        obj.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("Thread " + ref + " run");
            });
            thread.start();
        }
        try {
            Thread.sleep(3000l);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("main thread notifyAll after 3s");
        synchronized (obj) {
            obj.notifyAll();
        }
    }
}
