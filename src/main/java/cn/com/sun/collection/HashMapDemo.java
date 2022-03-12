package cn.com.sun.collection;

import java.util.HashMap;

public class HashMapDemo {
    public static void main(String[] args) {
        HashMap<String, Integer> hashMap = new HashMap<>();
        hashMap.put("sunchao", 2);
        hashMap.put("sunchao", 1);
        System.out.println(hashMap.get("sunchao"));
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
