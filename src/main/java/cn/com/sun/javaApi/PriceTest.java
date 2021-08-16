package cn.com.sun.javaApi;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

/**
 * @Description :
 * @Author :sunchao
 * @Date: 2020-04-27 22:38
 */
public class PriceTest {

    @ParameterizedTest
    @ValueSource(doubles = {12899.13})
    public void luNengPrice(double price) {
        double area = 117.25;
        double totalPrice = price * area;
        double finalTotalPrice = (totalPrice - 20000 - 10000) * 0.96 * 0.995;
        double finalPrice = finalTotalPrice / area;
        double firstPay = finalTotalPrice * 0.3;
        double loan = finalTotalPrice * 0.7;
        System.out.println("优惠后总价：" + finalTotalPrice);
        System.out.println("优惠后单价：" + finalPrice);
        System.out.println("首付：" + firstPay);
        System.out.println("贷款：" + loan);
    }

}
