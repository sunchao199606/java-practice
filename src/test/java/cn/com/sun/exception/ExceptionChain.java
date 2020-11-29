package cn.com.sun.exception;

/**
 * @Description : 异常链
 * @Author : mockingbird
 * @Date : 2020/9/21 14:38
 */
public class ExceptionChain {

    public static void fun() throws Exception {
        try {
            System.out.println(1 / 0);
        } catch (Exception e) {
            throw new Exception("aaaa", e);
        }
    }


    public static void main(String[] args) throws Exception {
        fun();
    }
}
