package cn.com.sunchao.reflection.dynamicProxy.cglib;

/**
 * @author ：sunchao
 * @date ：Created in 2020/1/16 9:31
 * @description：被代理类
 */
public class HelloServiceImpl {
    public void sayHello() {
        System.out.println("Hello Zhanghao");
    }

    public void sayBey() {
        System.out.println("Bye Zhanghao");
    }

    void sayGood() {
        System.out.println("Good Zhanghao");
    }
}
