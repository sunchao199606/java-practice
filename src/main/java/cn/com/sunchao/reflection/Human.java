package cn.com.sunchao.reflection;

public class Human {

	@SuppressWarnings("unused")
	private String parentPrivateField;

	protected String parentProtectedField;

	public String parentPublicField;

	@SuppressWarnings("unused")
	private void parentPrivateMethod() {

	}

	protected void parentProtectedMethod() {

	}

	public void parentPublicMethod() {

	}

	public Human(String field1, String field2, String field3) {
		this.parentPrivateField = field1;
		this.parentProtectedField = field2;
		this.parentPublicField = field3;
	}

	public Human() {
		System.out.println("parent constuctor");
	}

}
