package cn.com.sunchao.classloader;

/**
 * @author ：sunchao
 * @date ：Created in 2019/12/6 13:45 @description： ClassLoader测试
 */
public class MyClassLoader extends ClassLoader {

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		return super.loadClass(name);
	}

	@SuppressWarnings("rawtypes")
	public static void main(String[] args)
			throws ClassNotFoundException, IllegalAccessException, InstantiationException {
		MyClassLoader loader = new MyClassLoader();
		Class clazz = loader.loadClass("cn.com.sunchao.classloader.SomeClass");
		clazz.newInstance();
	}
}
