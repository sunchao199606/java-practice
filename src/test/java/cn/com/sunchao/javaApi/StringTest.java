package cn.com.sunchao.javaApi;

import org.junit.jupiter.api.Test;

/**
 * @Description : 字符串测试
 * @Author :sunchao
 * @Date: 2020-07-13 13:39
 */
public class StringTest {

    @Test
    public void add() {
//        String a = "a";
//        String b = "b";
//        String c = a + b;
//        System.out.println(c);
        int i = 8;
        float f = 3.0f;
        System.out.println((int) (i / f));
    }

    @Test
    public void test() {
        String a = "孙超;;;;;;;;;;;;;;;jjjjjjj";
        String b = "a";
        System.out.println(a);
        Class clazz = StringTest.class;
        clazz.getEnclosingMethod();
    }
}
