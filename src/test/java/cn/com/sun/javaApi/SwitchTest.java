package cn.com.sun.javaApi;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * @Description : switch测试
 * @Author : Mockingbird
 * @Date : 2020-07-29 18:17
 */
public class SwitchTest {

    @ParameterizedTest
    @ValueSource(strings = {""})
    public void method(String param) {
        switch (param) {
            // 肯定不是进入这里
            case "sth":
                System.out.println("it's sth");
                break;
            // 也不是进入这里
            case "null":
                System.out.println("it's null");
                break;
            // 也不是进入这里
            default:
                System.out.println("default");
        }
    }

}
