package cn.com.sunchao.reflection.dynamicProxy.jdk;

import sun.misc.ProxyGenerator;

import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @author ：sunchao
 * @date ：Created in 2020/1/15 16:27
 * @description：代理类
 */
public class ProductInvocationHandler implements InvocationHandler {
    // 目标对象
    private Object target;

    /**
     * 构造方法
     *
     * @param target 目标对象
     */
    public ProductInvocationHandler(Object target) {
        this.target = target;
    }


    /**
     * 执行目标对象的方法
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        // 在目标对象的方法执行之前简单的打印一下
        System.out.println("检查产品");
        // 执行目标对象的方法
        Object result = method.invoke(target, args);
        // 在目标对象的方法执行之后简单的打印一下
        System.out.println("添加完成");

        return result;
    }

    /**
     * 获取目标对象的代理对象
     *
     * @return 代理对象
     */
    public Object getProxy() {
        return Proxy.newProxyInstance(Thread.currentThread().getContextClassLoader(),
                target.getClass().getInterfaces(), this);
    }

    public static void main(String[] args) throws IOException {
        // 生成字节码
        byte[] data = ProxyGenerator.generateProxyClass("cn.com.sunchao.reflection.dynamicProxy.jdk.ProductServiceImpl", ProductServiceImpl.class.getInterfaces());
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(data);
        File proxyClassFile = new File("D:\\Program\\workspace\\eclipse\\demo_workspace\\JavaDemo\\out\\production\\classes\\cn\\com\\sunchao\\reflection\\dynamicProxy\\ProductServiceImpl$Proxy.class");
        if (!proxyClassFile.exists()) {
            proxyClassFile.createNewFile();
        }
        FileOutputStream fileOutputStream = new FileOutputStream(proxyClassFile);
        byte[] container = new byte[512];
        while (byteArrayInputStream.read(container) != -1) {
            fileOutputStream.write(container);
        }
        ProductServiceImpl target = new ProductServiceImpl();
        ProductInvocationHandler handler = new ProductInvocationHandler(target);
        ProductService proxy = (ProductService) handler.getProxy();
        proxy.addProduct("milk");
    }
}
