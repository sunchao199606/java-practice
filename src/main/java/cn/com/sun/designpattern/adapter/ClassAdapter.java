package cn.com.sun.designpattern.adapter;

/**
 * @Description : 类适配器
 * @Author : Mockingbird
 * @Date : 2020-08-07 12:30
 */

class Gas {
    void fire() {
        System.out.println("点火");
    }
}

interface ICook {
    void cook();
}

public class ClassAdapter extends Gas implements ICook{
    @Override
    public void cook() {

    }
}
