package cn.com.sun.concurrent.threadlocal;

// 线程本地变量，在哪个线程中赋的值，赋值的线程中获取使用，在其他线程中获取不到 本质是将对象与线程绑定
// 使用此特性可以实现对象与线程绑定，在特定的线程中拿到特定的对象，实现数据隔离
// 起源是如果多线程同时set一个对象，那么get时，拿到的对象可能是另一个线程设置的对象。
// 前提条件：1.两个线程 2.同时给某对象设置两个不同的对象属性 2.两个线程获取该对象的对象属性时触发数据混乱
public class Main {
    public static void main(String[] args) {
        // 代码在主线程执行
        Object objMain = new Object();
        Object obj2 = new Object();
        // 线程共享对象
        Computer computer = new Computer();

        // 新起一个线程 获取obj
        Thread thread = new Thread(() -> {
            computer.setObj(obj2);
            computer.setObject(obj2);
            System.out.println("other obj" + computer.getObj());
            System.out.println("other object" + computer.getObject());
        });
        thread.start();
        computer.setObj(objMain);
        computer.setObject(objMain);
        System.out.println("main obj" + computer.getObj());
        System.out.println("main object" + computer.getObject());
        // 输出
//        main objjava.lang.Object@193b845
//        other objjava.lang.Object@fae977
//        main objectjava.lang.Object@fae977
//        other objectjava.lang.Object@fae977
    }
}
