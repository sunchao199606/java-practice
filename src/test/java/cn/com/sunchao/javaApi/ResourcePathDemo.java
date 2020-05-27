package cn.com.sunchao.javaApi;


import org.junit.jupiter.api.Test;

public class ResourcePathDemo {

	@Test
	public void testClassLoader() {

		System.out.println(this.getClass().getClassLoader().getResource(""));
		System.out.println(this.getClass().getClassLoader().getResource("file"));
		System.out.println(this.getClass().getResource(""));
		System.out.println(this.getClass().getResource("file"));
	}
}
