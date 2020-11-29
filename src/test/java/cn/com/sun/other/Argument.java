package cn.com.sun.other;

/**
 * @author SunChao
 *
 * @date 2019年8月15日
 *
 * @description 
 */
public class Argument {
	
	private static void showStrings (String... strings) {
		for (String string :strings) {
			System.out.println(string);
		}
	}
	
	public static void main(String[] args) {
		String[] strings = new String[] {"1","2"};
		String string = new String();
		// List<String> stringList = new ArrayList<String>();
		// showStrings(stringList);
		showStrings(strings);
		showStrings(string);
	}

}
