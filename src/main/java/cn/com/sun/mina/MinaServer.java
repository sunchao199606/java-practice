package cn.com.sun.mina;

import org.apache.mina.core.service.IoProcessor;
import org.apache.mina.transport.socket.SocketAcceptor;
import org.apache.mina.transport.socket.nio.NioProcessor;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;

import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author ：sunchao
 * @date ：Created in 2020/1/19 16:05
 * @description：Mina Server
 */
public class MinaServer {

    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(
                9, Integer.MAX_VALUE, 3600L, TimeUnit.SECONDS,
                new SynchronousQueue<Runnable>(), new ThreadFactory() {
            private AtomicInteger acceptorNumber = new AtomicInteger(
                    0);

            private AtomicInteger processorNumber = new AtomicInteger(
                    0);

            public Thread newThread(Runnable r) {
                String currentGroupName = Thread.currentThread()
                        .getThreadGroup().getName();
                if (currentGroupName.startsWith("Acceptor")) {
                    String name = currentGroupName + ".Processor"
                            + processorNumber.getAndIncrement();
                    ThreadGroup group = new ThreadGroup(name);
                    return new Thread(group, r, name, 0);
                } else {
                    ThreadGroup group = new ThreadGroup("Acceptor"
                            + acceptorNumber.getAndIncrement());
                    return new Thread(group, r, group.getName());
                }
            }
        });
        IoProcessor processor = new NioProcessor(threadPoolExecutor);
        SocketAcceptor acceptor = new NioSocketAcceptor(processor);
//        SocketAddress address = new So
//        acceptor.bind();
    }
}
