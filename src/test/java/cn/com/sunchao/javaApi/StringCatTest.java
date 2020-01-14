package cn.com.sunchao.javaApi;

public class StringCatTest {

	public static void main(String[] args) {
		printResult(100);
		System.out.println("***********************************************");
		printResult(1000);
		System.out.println("***********************************************");
		printResult(10000);
		System.out.println("***********************************************");
		printResult(100000);
		System.out.println("***********************************************");
		printResult(1000000);
		System.out.println("***********************************************");
		printResult(10000000);

	}

	public static void printResult(long loopCount) {
		System.out.println("loopCount:" + loopCount);
		stringCat(loopCount);
		stringBuilderAppend(loopCount);
		stringBufferAppend(loopCount);
	}

	public static long stringCat(long loopCount) {
		long beginTime = System.currentTimeMillis();
		String str = "hello,world!";
		@SuppressWarnings("unused")
		String result = "";

		for (int i = 0; i < loopCount; i++) {
			result += str;
		}
		long consumeTime = System.currentTimeMillis() - beginTime;
		System.out.println("String cat time:" + consumeTime);
		return consumeTime;
	}

	public static long stringBuilderAppend(long loopCount) {
		long beginTime = System.currentTimeMillis();
		String str = "hello, world!";
		@SuppressWarnings("unused")
		String result = "";
		StringBuilder stringBuilder = new StringBuilder();

		for (int i = 0; i < loopCount; i++) {
			stringBuilder.append(str);
		}
		result = stringBuilder.toString();
		long consumeTime = System.currentTimeMillis() - beginTime;
		System.out.println("StringBuilder append time:" + consumeTime);
		return consumeTime;

	}

	public static long stringBufferAppend(long loopCount) {
		long beginTime = System.currentTimeMillis();
		String str = "hello, world!";
		@SuppressWarnings("unused")
		String result = "";
		StringBuffer stringBuffer = new StringBuffer();

		for (int i = 0; i < loopCount; i++) {
			stringBuffer.append(str);
		}
		result = stringBuffer.toString();
		long consumeTime = System.currentTimeMillis() - beginTime;
		System.out.println("StringBuffer append time:" + consumeTime);
		return consumeTime;
	}

}
