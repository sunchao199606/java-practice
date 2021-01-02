package cn.com.sun.java8;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class MethodReference {
    public static void main(String[] args) {
        Phone phone = Phone.create(Phone::new);
        List<Phone> phones = Arrays.asList(phone);
        //phones.forEach();
        Arrays.asList("").forEach(phone::addComponent);
        phones.forEach(Phone::camera);
    }
}


class Phone {
    public static void camera(Phone phone) {
        System.out.println(phone.os());
    }

    public String os() {
        return "ios";
    }

    public static Phone create(Supplier<Phone> supplier) {
        return supplier.get();
    }

    public void addComponent(String component) {

    }
}
