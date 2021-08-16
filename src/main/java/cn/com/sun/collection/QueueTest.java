package cn.com.sun.collection;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.*;

/**
 * @Description : 队列测试
 * @Author : Mockingbird
 * @Date : 2020-08-08 01:26
 */
public class QueueTest {

    @Test
    public void testQueue() {
        Queue<Runnable> queue = new SynchronousQueue<>();
        // 非阻塞队列
        Queue<Integer> linkedList = new LinkedList<>();
        Queue<Integer> concurrentLinkedQueue = new ConcurrentLinkedQueue<>();
        Queue<Integer> arrayDeque = new ArrayDeque<>();
        linkedList.offer(1);
        linkedList.offer(2);
        System.out.println(linkedList.poll() + linkedList.poll());

        // 阻塞队列
        BlockingQueue<Object> arrayBlockingQueue = new ArrayBlockingQueue<>(10);
        BlockingQueue<Object> linkedBlockingQueue = new LinkedBlockingQueue<>(10);
        BlockingQueue<RunnableScheduledFuture> delayQueue = new DelayQueue<>();
        BlockingQueue<Object> synchronousQueue = new SynchronousQueue<>();
        BlockingQueue<Object> linkedBlockingDeque = new LinkedBlockingDeque<>();
        BlockingQueue<Object> priorityBlockingQueue = new PriorityBlockingQueue<>();
    }

    @Test
    public void testBlockingQueue() throws InterruptedException {
        BlockingQueue<Integer> blockingQueue = new ArrayBlockingQueue<>(1);
        blockingQueue.offer(1);
        //System.out.println(blockingQueue.add(2));
        Thread thread = new Thread(() -> {
            try {
                blockingQueue.offer(2, 2, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                System.out.println("catch");
                e.printStackTrace();
            }
        });
        thread.start();
        Thread.sleep(1000);
        thread.interrupt();
        //System.out.println(blockingQueue.offer(2, 2, TimeUnit.SECONDS));
    }

    /**
     * SynchronousQueue 阻塞队列 不会缓存任务
     *
     * @throws InterruptedException
     */
    @Test
    public void testSynchronousQueue() throws InterruptedException {
        class Message {
        }
        Message message = new Message();
        BlockingQueue<Object> queue = new SynchronousQueue<>();
//        new Thread(() -> {
//            try {
//                queue.put(message);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        }).start();
        queue.offer(1);
        new Thread(() -> {
            try {
                System.out.println(queue.take());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
