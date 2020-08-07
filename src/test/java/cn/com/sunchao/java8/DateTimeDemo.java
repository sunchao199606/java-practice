package cn.com.sunchao.java8;

import java.time.Duration;
import java.time.LocalDate;
import java.time.Period;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class DateTimeDemo {

	@SuppressWarnings("unused")
	private static void localDateTime() {
		LocalDate date = LocalDate.now();
		// 计算本日期所属周的周几日期
		System.out.println(date.with(ChronoField.DAY_OF_WEEK, 5));
		// 计算本日期添加10天之后的日期
		System.out.println(date.with(t -> t.plus(10, ChronoUnit.DAYS)));
		System.out.println(date.with(t -> t.plus(10, ChronoUnit.MONTHS)));
		System.out.println(date.with(TemporalAdjusters.lastDayOfYear()));
		System.out.println(date.range(ChronoField.DAY_OF_YEAR));
		System.out.println(date.until(date.with(TemporalAdjusters.lastDayOfYear()), ChronoUnit.DAYS));
	}

	@SuppressWarnings("unused")
	private static void period() {
		Period period = Period.between(LocalDate.of(2019, 9, 19), LocalDate.of(2019, 10, 18));

		System.out.println(period.get(ChronoUnit.MONTHS));
		System.out.println(period.getChronology());
		System.out.println(period.getUnits());
		Duration duration = Duration.of(2l, ChronoUnit.MONTHS);
		System.out.println(duration.toDays());
	}

	private static void compare() {
		System.out.println(LocalDate.of(2019, 9, 18).compareTo(LocalDate.of(2019, 9, 19)));
	}

	public static void main(String[] args) {

		// localDateTime();
		// period();
		compare();
	}
}
