package cn.com.sunchao.javaApi;

import java.util.concurrent.ExecutionException;

public class FutureDemo {

	private static void fun3() throws Exception {
		Throwable cause = new NullPointerException();
		throw new ExecutionException(cause);
	}

	private static void fun2() throws Exception {
		fun3();
	}

	private static void fun1() throws Exception {
		fun2();
	}

	private static void fun0() throws Exception {
		fun1();
	}

	public static void main(String[] args) {
		try {
			fun0();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
