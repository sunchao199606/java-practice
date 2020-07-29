package cn.com.sunchao.classloader;

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

    public static void main(String[] args) {
        new SomeClass();
    }
}
