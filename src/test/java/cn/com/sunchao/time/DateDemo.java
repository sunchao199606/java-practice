package cn.com.sunchao.time;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateDemo {

	public static void main(String[] args) {
		Date date = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("HHmmss");
		String dateNowString = formatter.format(date);
		System.out.println(dateNowString);
	}

}
