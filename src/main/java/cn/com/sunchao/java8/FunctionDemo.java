package cn.com.sunchao.java8;

import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class FunctionDemo {
	private static final Random RANDOM = new Random();

	// 一个入参的函数
	Function<String, Integer> f = s -> s.length();

	// 两个入参的函数
	BiFunction<Integer, Integer, Integer> function = (arg1, arg2) -> arg1 * arg2;

	// Supplier
	Supplier<Integer> supplier = () -> RANDOM.nextInt();

	// Consumer
	Consumer<String> consumer = s -> System.out.println(s);

	// BiConsumer
	BiConsumer<String, Integer> biConsumer = (key, value) -> System.out.println(key + value);

	// Predicate
	Predicate<String> predicate = s -> s.startsWith("66");

	BinaryOperator<Integer> biOperator = (operator1, operator2) -> operator1 + operator2;

	public static void main(String[] args) {

	}
}
