package cn.com.sun.other;

/**
 * @author ：sunchao
 * @date ：Created in 2019/12/6 15:49
 * @description：Son
 */
public class Son extends Father {
    public Son(Object arg) {
        System.out.println("son");
    }

    @Override
    protected void call() {
        System.out.println("Son Called");
    }


    public static void main(String[] args) {
        Son son = new Son(null);
        son.call();
    }
}
