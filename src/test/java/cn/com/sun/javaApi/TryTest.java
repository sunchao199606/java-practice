package cn.com.sun.javaApi;

import org.junit.jupiter.api.Test;

/**
 * @Description : TryCatchTest
 * @Author :sunchao
 * @Date: 2020-07-19 11:01
 */
public class TryTest {
    @Test
    public void tryTest() {
        try {
            throw new NullPointerException();
        } catch (NullPointerException e) {
            e.printStackTrace();
            System.out.println("catch a err and return");
            return;
        } finally {
            System.out.println("finally execute");
        }
    }
}
