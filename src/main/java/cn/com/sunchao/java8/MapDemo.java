package cn.com.sunchao.java8;

import java.util.HashMap;
import java.util.Map;

public class MapDemo {

    public static void main(String[] args) {
        Map<String, Integer> map = new HashMap<>();
        map.put("sun", 23);
        map.put("wang", 19);
        map.put("gao", 34);
        map.compute("sun", (k, v) -> v = v + 1);
        map.computeIfAbsent("11", k -> 10);
        map.computeIfPresent("11", (k, v) -> v = 100);
        System.out.println(map.get("11"));
    }

}
