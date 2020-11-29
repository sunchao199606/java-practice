package cn.com.sun.generics;

import java.util.ArrayList;

/**
 * @author ：sunchao
 * @date ：Created in 2020/1/17 16:48
 * @description： 泛型类
 */
public class ClassA<T> {
    T obj;

    public T getObj() {
        return obj;
    }

    public void setObj(T obj) {
        this.obj = obj;
    }

    /**
     * 泛型方法
     *
     * @param t
     * @param <T>
     */
    static <T> void show(T t) {

    }

    public static void main(String[] args) {
        ClassA<? extends ArrayList> instance = new ClassA<>();
        ArrayList obj = instance.getObj();
        show(new Object());
    }
}
