package cn.com.sunchao.java8;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

/**
 * @Description : java8流式计算
 * @Author : Mockingbird
 * @Date : 2020-08-15 11:32
 */
public class StreamDemo {
    @Test
    public void testReduce() {
        //new ArrayList<>().sort(Comparator.comparingInt(Object::hashCode));
        System.out.println(IntStream.rangeClosed(0, 0).reduce(100000, Integer::sum));
    }
}
