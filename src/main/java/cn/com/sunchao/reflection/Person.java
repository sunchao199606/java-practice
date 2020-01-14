package cn.com.sunchao.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.time.LocalDate;

public class Person extends Human {
	@SuppressWarnings("unused")
	private static LocalDate birthday = LocalDate.of(1996, 6, 16);

	public Person() {

	}

	public static void main(String[] args)
			throws InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException,
			NoSuchFieldException, SecurityException, NoSuchMethodException {
		Class<Person> clazz = Person.class;
//		Constructor<?>[] constructors = clazz.getDeclaredConstructors();
//		Field[] fields = clazz.getDeclaredFields();
//		clazz.getMethod("name");
//		for (Field field : fields) {
//			System.out.println(field.getName());
//		}
//		// field.setAccessible(true);
//		for (Constructor<?> constructor : constructors) {
//			@SuppressWarnings("unused")
//			Person person = (Person) constructor.newInstance();
//		}
		@SuppressWarnings("unused")
		Field field = clazz.getDeclaredField("birthday");
		// field.get()
	}
}
