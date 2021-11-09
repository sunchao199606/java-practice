package cn.com.sun.other;

/**
 * @author ：sunchao
 * @date ：Created in 2019/12/6 15:48
 * @description：Father
 */
public class Father extends GrandFather {

    public Father(Object arg) {
        System.out.println("father arg");
    }

    public Father(){
        System.out.println("father");
    }

    @Override
    protected void call() {
        System.out.println("Father Called");
    }
}
