package cn.com.sunchao.encode;

import org.apache.commons.codec.binary.Hex;

import java.io.UnsupportedEncodingException;

public class CompareEncode {

    public static void main(String[] args) {
        new CompareEncode().testEncoder();
    }

    public void testEncoder(){
        String name = "中文";

        char[] chars = name.toCharArray();
        for (char c : chars) {
            System.out.printf(c +"（"+(int)c+ "）=" + Integer.toHexString(c) +" | ");
        }
        System.out.println();

        try {
            byte[] iso8859 = name.getBytes("ISO-8859-1");
            System.out.println("iso:");
            toHex(iso8859);

            byte[] utf8 = name.getBytes("UTF-8");
            System.out.println("utf8:");
            toHex(utf8);

            byte[] gb2312 = name.getBytes("GB2312");
            System.out.println("gb2312:");
            toHex(gb2312);

            byte[] gbk = name.getBytes("GBK");
            System.out.println("gbk:");
            toHex(gbk);

            byte[] utf16 = name.getBytes("UTF-16");
            System.out.println("utf16:");
            toHex(utf16);


        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private void toHex(byte[] data) {
        for (byte b: data){
            byte[] bytes = {b};
            System.out.printf(Hex.encodeHexString(bytes) + "           | ");
        }
        System.out.println();
    }
}
