package cn.com.sun.classloader;

/**
 * @author ：sunchao
 * @date ：Created in 2019/12/6 13:45 @description： ClassLoader测试
 */
public class MyClassLoader extends ClassLoader {

    @SuppressWarnings("rawtypes")
    public static void main(String[] args)
            throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        MyClassLoader loader = new MyClassLoader();
        Class clazz = loader.loadClass("cn.com.sun.classloader.SomeClass");
        clazz.newInstance();
    }
}
