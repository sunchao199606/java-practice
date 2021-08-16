package cn.com.sun.javaApi;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

/**
 * @Description :
 * @Author : mockingbird
 * @Date : 2021/3/18 12:34
 */
public class BigDecimalTest {

    @Test
    public void test() {
        BigDecimal decimal = new BigDecimal("5.00");
        BigDecimal setScale = decimal.setScale(10, BigDecimal.ROUND_HALF_DOWN);
        System.out.println(setScale.toPlainString());
    }
}
