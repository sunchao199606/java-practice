package cn.com.sun.enums;

public enum EnumTest {
	LIU("The family name of me"), FRANK("The given name of me");
	private String context;

	private String getContext() {
		return this.context;
	}

	private EnumTest(String context) {
		this.context = context;
	}

	public static void main(String[] args) {
		for (EnumTest name : EnumTest.values()) {
			System.out.println(name + " : " + name.getContext());
		}
		System.out.println(EnumTest.FRANK.getDeclaringClass());
	}
}
