package cn.com.sunchao.javaApi;



import org.junit.jupiter.api.Test;

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
}
