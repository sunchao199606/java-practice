package cn.com.sunchao.java8;

public class ThreadLocalDemo {

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		// 线程本地变量 只在线程本线程可见
		ThreadLocal<String> mainThreadStr = new ThreadLocal<String>();
		mainThreadStr.set("mainThreadStr");

		String normalStr = "normalStr";

		Thread newThread = new Thread(() -> {

			System.out.println(mainThreadStr.get());

			System.out.println(normalStr);

		});

		newThread.start();
		try {
			Thread.currentThread().sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		System.out.println(mainThreadStr.get());
	}
}
