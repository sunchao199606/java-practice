package cn.com.sun.javaApi;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Description : Map测试
 * @Author :sunchao
 * @Date: 2020-07-09 17:47
 */
public class MapTest {

    @Test
    public void testHashMap() {
        Object object1 = new Object();
        System.out.println("object1:" + object1);
        Object object2 = new Object();
        System.out.println("object2:" + object2);
        Map<String, Object> map = new HashMap<>();
        map.put("key1", object1);
        map.put("key2", object1);
        //map.put("key1", object2);
        System.out.println("key1:" + map.get("key1"));
        System.out.println("key2:" + map.get("key2"));
//        map.putIfAbsent("key1", object2);
//        System.out.println("key1:" + map.get("key1"));

    }


    @Test void  testLinkedHashMap(){
        Map<String,Object> map = new LinkedHashMap<>();
        //map.put();
    }
}
