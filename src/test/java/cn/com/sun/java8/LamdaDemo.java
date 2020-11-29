package cn.com.sun.java8;

import java.util.concurrent.atomic.AtomicInteger;

public class LamdaDemo {

	// @SuppressWarnings("static-access")
	public static void main(String[] args) throws InterruptedException {
		AtomicInteger integer = new AtomicInteger(0);
		Runnable task = () -> {
			integer.getAndIncrement();
			// System.out.println(name.get());
		};
		Thread thread = null;
		for (int i = 0; i < 10000; i++) {
			thread = new Thread(task);
			thread.start();
		}
		thread.join();
		// Thread.currentThread().sleep(5000);
        System.out.println(integer.get());
	}

}
