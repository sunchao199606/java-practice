package cn.com.sunchao.java8;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DateTimeDemoTest {

	@Test
	public void testTimeCompare() {
		DateTimeDemo dateTimeDemo = new DateTimeDemo();
		assertTrue(dateTimeDemo.timeCompare());
	}

}
