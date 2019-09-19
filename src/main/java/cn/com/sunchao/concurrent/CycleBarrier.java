package cn.com.sunchao.concurrent;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

public class CycleBarrier {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		CyclicBarrier barrier = new CyclicBarrier(10);
		AtomicInteger id = new AtomicInteger(0);
		for (int i = 0; i < 8; i++) {
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
	}
}
