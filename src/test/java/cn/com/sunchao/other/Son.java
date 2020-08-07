package cn.com.sunchao.other;

/**
 * @author ：sunchao
 * @date ：Created in 2019/12/6 15:49
 * @description：Son
 */
public class Son extends Father {

    @Override
    protected void call() {
        System.out.println("Son Called");
    }


    public static void main(String[] args) {
        Son son = new Son();
        son.call();
    }
}
