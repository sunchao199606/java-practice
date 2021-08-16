package cn.com.sun.javaApi;


import org.junit.jupiter.api.Test;

import java.net.URI;
import java.time.Duration;

/**
 * @Description : 各种测试
 * @Author :sunchao
 * @Date: 2020-04-17 10:09
 */
public class MainTest {
    @Test
    public void testDuration() {
        Duration duration = Duration.ofHours(3);
        System.out.println(duration.toHours());
        System.out.println(duration.toMinutes());
//        LocalTime lt = LocalTime.now();
//        System.out.println(lt.getHour() + lt.getMinute());
//        System.out.println(Float.compare(1,1.5f));
        System.out.println((long) 100 / 66);
    }

    @Test
    public void testAcc() {
        float f = 321.1415926f;
        int i = 100;
        //float af = Math.round(f * 100) / 100f;
        System.out.println(f * i);
    }

    private boolean fun() {
        boolean connected = true;
        try {
            URI uri = new URI("");
            if (uri != null) {
                throw new Exception();
            }
        } catch (Exception exception) {
            connected = false;
            exception.printStackTrace();
        }
        return connected;
    }

    @Test
    public void testReturn() {
        System.out.println(fun());
    }
}
