package cn.com.sun.java8;

import java.util.Random;
import java.util.stream.IntStream;

public class RandomDemo {

    public static void main(String[] args) {
        Random random = new Random();

        IntStream.rangeClosed(1, 20).forEach(i ->{
            System.out.println(random.nextInt(4));
        });

    }
}
