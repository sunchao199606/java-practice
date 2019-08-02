package cn.com.sunchao.java8;

import java.time.LocalTime;

public class DateTimeDemo {
	
	public boolean timeCompare() {
		LocalTime time1 = LocalTime.of(22, 0);
		return time1.plusHours(10).compareTo(LocalTime.of(9, 0)) == -1;
	}
}
