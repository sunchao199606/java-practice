package cn.com.sunchao.reflection.dynamicProxy.cglib;

import cn.com.sunchao.reflection.dynamicProxy.cglib.HelloMethodInterceptor;
import cn.com.sunchao.reflection.dynamicProxy.cglib.HelloServiceImpl;
import net.sf.cglib.proxy.Enhancer;

/**
 * @author ：sunchao
 * @date ：Created in 2020/1/16 9:33
 * @description：测试
 */
public class Client {
    public static void main(String[] args) {
        //System.setProperty(DebuggingClassWriter.DEBUG_LOCATION_PROPERTY, "/Users/zhanghao/Documents/toy/spring-framework-source-study/");
        Enhancer enhancer = new Enhancer();
        //继承被代理类
        enhancer.setSuperclass(HelloServiceImpl.class);
        //设置回调
        enhancer.setCallback(new HelloMethodInterceptor());
        //设置代理类对象
        HelloServiceImpl helloService = (HelloServiceImpl) enhancer.create();
        //在调用代理类中方法时会被我们实现的方法拦截器进行拦截
        helloService.sayGood();
    }
}

