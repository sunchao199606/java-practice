package cn.com.sunchao.classloader;

/**
 * @author ：sunchao
 * @date ：Created in 2019/12/6 13:47
 * @description：will be loaded
 */
public class SomeClass {

    static {
        System.out.println(" SomeClass be loaded..");
    }
}
