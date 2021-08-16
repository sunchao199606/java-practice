package cn.com.sun.java8;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

public class PeriodSlice {

	private static int slice(LocalDate start, LocalDate end, int num, ChronoUnit unit) {
		if (unit == ChronoUnit.DAYS) {
			return (int) Math.ceil(((float) (start.until(end).getDays() + 1) / num));
		} else if (unit == ChronoUnit.WEEKS) {
			return (int) start.with(TemporalAdjusters.previous(DayOfWeek.MONDAY))
					.until(end.with(TemporalAdjusters.next(DayOfWeek.MONDAY)), ChronoUnit.DAYS) / (7 * num);
		} else if (unit == ChronoUnit.MONTHS) {
			return start.with(TemporalAdjusters.firstDayOfMonth())
					.until(end.with(TemporalAdjusters.firstDayOfNextMonth())).getMonths();
		} else {
			return 0;
		}
	}

	public static void main(String[] args) {
		LocalDate start = LocalDate.of(2019, 9, 1);
		LocalDate end = LocalDate.of(2019, 9, 30);
		System.out.println((start.until(end).getDays() + 1) / 7.0);
		System.out.println(slice(start, end, 1, ChronoUnit.MONTHS));
		System.out.println((int) Math.ceil(3.2));
	}
}
