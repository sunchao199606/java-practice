package cn.com.sun.collection;

import java.util.Arrays;

/**
 * @Description : ListDemo
 * @Author :sunchao
 * @Date: 2020-04-16 00:02
 */
public class ListDemo {

    public static void main(String[] args) {
        //List<Integer> list = new ArrayList<>();
        Integer[] integers = new Integer[10];
        System.out.println(integers == Arrays.copyOf(integers, 20));
    }
}
