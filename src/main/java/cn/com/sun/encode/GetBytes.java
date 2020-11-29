package cn.com.sun.encode;

import java.io.UnsupportedEncodingException;

public class GetBytes {

    public static void main(String[] args) {
        String string = "中文";
        byte[] bytes = null;
        try {
            bytes = string.getBytes("iso8859-1");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        StringBuffer sb = new StringBuffer(bytes.length);
        String sTmp;

        for (byte b : bytes) {
            System.out.print(b + " ");
        }
        for (int i = 0; i < bytes.length; i++) {
            sTmp = Integer.toHexString(0xFF & bytes[i]);
            if (sTmp.length() < 2)
                sb.append(0);
            sb.append(sTmp.toUpperCase());
        }

        System.out.println(sb);
    }
}
