package cn.com.sun.classloader;

import sun.reflect.CallerSensitive;
import sun.reflect.Reflection;

/**
 * @author ：sunchao
 * @date ：Created in 2019/12/6 13:47
 * @description：will be loaded
 */
public class SomeClass extends Thread {

    static {
        System.out.println("SomeClass be loaded..");
    }

    private static String string = "mmm";

    static {
        System.out.println(string);
    }

    private Object o = new Object();

    public void method() {
        String s = "aaa";
        int a = 10;
    }
    @CallerSensitive
    public static void call(){
        Class<?> clazz = Reflection.getCallerClass();
        System.out.println("Hello " + clazz);
    }
    @CallerSensitive
    public static void main(String[] args) {

       SomeClass.call();
    }
}
