package cn.com.sun.collection;

import java.util.HashSet;
import java.util.Set;

/**
 * @Author : mockingbird
 * @Date : 2022/3/18 17:51
 * @Description :
 */
public class SetDemo {
    public static void main(String[] args) {
        HashSet<String> hashSet = new HashSet<>();
        hashSet.add("A");
        hashSet.add("A");
        hashSet.contains("A");
    }
}
