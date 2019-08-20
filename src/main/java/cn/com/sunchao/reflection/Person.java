package cn.com.sunchao.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

public class Person extends Human {

	public Person() {

	}

	public static void main(String[] args)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchFieldException, SecurityException, NoSuchMethodException {
		Class<Person> clazz = Person.class;
		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
		Field[] fields = clazz.getDeclaredFields();
		clazz.getMethod("name");
		for (Field field : fields) {
			System.out.println(field.getName());
		}
		// field.setAccessible(true);
		for (Constructor<?> constructor : constructors) {
			@SuppressWarnings("unused")
			Person person = (Person) constructor.newInstance();
		}
	}

}
