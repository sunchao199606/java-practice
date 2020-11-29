package cn.com.sun.designpattern.adapter;

/**
 * @Description : 对象适配器
 * @Author : Mockingbird
 * @Date : 2020-08-07 12:29
 */

interface Socket {
    void charge();
}

/**
 * 手机
 */
class Phone {
    void phoneCharge() {
        System.out.println("手机正在充电");
    }
}

/**
 * 电源适配器 采用组合模式
 */
public class ObjectAdapter implements Socket {
    private Phone phone;

    ObjectAdapter(Phone phone) {
        this.phone = phone;
    }

    @Override
    public void charge() {
        phone.phoneCharge();
    }
}



