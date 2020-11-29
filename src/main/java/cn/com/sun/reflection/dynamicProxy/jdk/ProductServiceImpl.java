package cn.com.sun.reflection.dynamicProxy.jdk;

/**
 * @author ：sunchao
 * @date ：Created in 2020/1/15 16:33
 * @description：被代理类
 */
public class ProductServiceImpl implements ProductService {

    @Override
    public void addProduct(String productName) {
        System.out.println("正在添加" + productName);
    }
}
