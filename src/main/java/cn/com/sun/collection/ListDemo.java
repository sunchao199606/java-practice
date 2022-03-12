package cn.com.sun.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;

/**
 * @Description : ListDemo
 * @Author :sunchao
 * @Date: 2020-04-16 00:02
 */
public class ListDemo {

    public static void main(String[] args) {
        //List<Integer> list = new ArrayList<>();
//        Integer[] integers = new Integer[10];
//        System.out.println(integers == Arrays.copyOf(integers, 20));
        ArrayList<Integer> arrayList = new ArrayList<>(10);
        Collections.synchronizedList(arrayList);
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.add(1);
        linkedList.add(1);
        linkedList.add(3);
        linkedList.add(5);
        int i = linkedList.get(4);
    }
}
