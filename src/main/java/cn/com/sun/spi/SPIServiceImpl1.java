package cn.com.sun.spi;

/**
 * @author ：sunchao
 * @date ：Created in 2020/1/16 16:39
 * @description：SPIService 实现
 */
public class SPIServiceImpl1 implements SPIService {

    @Override
    public void execute() {
        System.out.println("SPIServiceImpl1 execute...");
    }
}
