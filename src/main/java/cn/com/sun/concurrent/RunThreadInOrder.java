package cn.com.sun.concurrent;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author SunChao
 *
 * @date 2019年9月18日
 *
 * @description 多线程按顺序执行
 */
public class RunThreadInOrder {

	// 多线程同步的方法
	private static void sync() {
		Object lock1 = new Object();
		Object lock2 = new Object();

		Thread first = new Thread(() -> {
			synchronized (lock1) {
				System.out.println("first");
				lock1.notify();
			}
		}, "first");
		Thread second = new Thread(() -> {
			synchronized (lock1) {
				try {
					lock1.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				synchronized (lock2) {
					System.out.println("second");
					lock2.notify();
				}
			}
		}, "second");
		Thread third = new Thread(() -> {
			synchronized (lock2) {
				try {
					lock2.wait();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				System.out.println("third");
			}
		}, "third");
		third.start();
		second.start();
		first.start();
	}

	private static void join() {
		Thread first = new Thread(() -> {
			System.out.println("first");
		}, "first");
		Thread second = new Thread(() -> {
			try {
				first.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("second");
		}, "second");

		Thread third = new Thread(() -> {
			try {
				second.join();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			System.out.println("third");
		}, "third");
		third.start();
		second.start();
		first.start();
	}

	private static void reentrantLock() {
		ReentrantLock lock = new ReentrantLock();
		Condition condition1 = lock.newCondition();
		Condition condition2 = lock.newCondition();
		AtomicBoolean isFirstDone = new AtomicBoolean(false);
		AtomicBoolean isSecondDone = new AtomicBoolean(false);
		Thread first = new Thread(() -> {
			lock.lock();
			System.out.println("first");
			condition1.signalAll();
			lock.unlock();
			isFirstDone.set(true);
		}, "first");
		Thread second = new Thread(() -> {
			lock.lock();
			if (!isFirstDone.get()) {
				try {
					condition1.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("second");
			condition2.signalAll();
			lock.unlock();
			isSecondDone.set(true);
		}, "second");

		Thread third = new Thread(() -> {
			lock.lock();
			if (!isSecondDone.get()) {
				try {
					condition2.await();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			System.out.println("third");
			lock.unlock();
		}, "third");
		third.start();
		second.start();
		first.start();

	}

	public static void main(String[] args) {
		// 1 多线程同步
		sync();
		// 2 线程join方法
		join();
		// 3 reentrantLock
		reentrantLock();

	}

}
