package cn.com.sunchao.encode;

import java.io.*;

public class Properties {

    public static void main(String[] args) throws FileNotFoundException {
        FileInputStream fileInputStream = new FileInputStream(new File("D:\\Program\\workspace\\IDEA\\demo\\src\\main\\resources\\conf.properties"));
        try {
            int b;
            while ((b = fileInputStream.read()) != -1) {

                System.out.print(Integer.toHexString(b) + " ");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        java.util.Properties pro = new java.util.Properties();
        FileInputStream in = new FileInputStream("D:\\Program\\workspace\\IDEA\\demo\\src\\main\\resources\\conf.properties");
        try {
            pro.load(in);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println();

        System.out.println("###################################");
        String string = pro.getProperty("p1");
        System.out.println(string);
        try {
            byte[] bs = string.getBytes("ISO-8859-1");

            StringBuffer sb = new StringBuffer(bs.length);
            String sTemp;
            for (int i = 0; i < bs.length; i++) {
                sTemp = Integer.toHexString(0xFF & bs[i]);
                if (sTemp.length() < 2)
                    sb.append(0);
                sb.append(sTemp + " ");
            }
            System.out.println(sb);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
