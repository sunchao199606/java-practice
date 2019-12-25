package cn.com.sunchao.annotation;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * @author ：sunchao
 * @date ：Created in 2019/12/25 11:24
 * @description： Annotation Demo
 */
@Bad(scope = "class", list = {@Parameter(name = "AnnotationDemo")})
class AnnotationDemo {
    @Bad(scope = "field", list = {@Parameter(name = "annotation")})
    private static AnnotationDemo annotation;

    @Bad(scope = "method", list = {@Parameter(name = "get")})
    private static void get() {
    }

    public static void main(String[] args) {
        //反射获取该类的注解
        Class clazz = AnnotationDemo.class;
        Bad annotation = (Bad) clazz.getAnnotation(Bad.class);
        System.out.println(annotation.scope());
        //反射获取方法注解
        Method[] methods = clazz.getDeclaredMethods();
        for (Method method : methods) {
            if (!Modifier.isPublic(method.getModifiers())) {
                System.out.println(method.getAnnotation(Bad.class).list().length);
            }
        }
    }
}

@Documented
//指定annotation存在的阶段
@Retention(RetentionPolicy.RUNTIME)
//执行annotation作用对象
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.TYPE})
//@interface表示该类是一个注解 继承了java.lang.Annotation
@interface Bad {
    String scope() default "type";
    Parameter[] list();
}

@interface Parameter {
    String name();
}
