package cn.com.sun.collection;

import java.util.LinkedHashMap;
import java.util.TreeMap;

/**
 * @Author : mockingbird
 * @Date : 2022/3/18 17:13
 * @Description :
 */
public class TreeMapDemo {

    public static void main(String[] args) {
        TreeMap<String, String> treeMap = new TreeMap<>();
        treeMap.put("1", "1");
        treeMap.put("2", "2");
        treeMap.put("3", "3");
        treeMap.put("4", "4");
        System.out.println(treeMap.get("4"));
        LinkedHashMap<String,String> linkedHashMap = new LinkedHashMap<>();
    }
}
