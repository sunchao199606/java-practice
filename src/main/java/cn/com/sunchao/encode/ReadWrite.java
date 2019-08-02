package cn.com.sunchao.encode;

import java.io.*;

public class ReadWrite {

    public static void main(String[] args) {
        try {
            new ReadWrite().test_write_read_encoding();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void test_write_read_encoding() throws IOException {
        String file = this.getClass().getClassLoader().getResource("").getPath()+ File.separator+"test.txt";
        String charset = "UTF-8";

        // 写字符换转成字节流
        FileOutputStream outputStream = new FileOutputStream(file);
        OutputStreamWriter writer = new OutputStreamWriter(
                outputStream, charset);
        try {
            writer.write("这是要保存的中文字符");
        } finally {
            writer.close();
        }

        // 读取字节转换成字符
        FileInputStream inputStream = new FileInputStream(file);
        InputStreamReader reader = new InputStreamReader(
                inputStream);

        StringBuilder sb = new StringBuilder();

        int charRead = reader.read();
        while (charRead != -1){
            sb.append((char) charRead);
            charRead = reader.read();
        }

        System.out.println(sb.toString());
        reader.close();
    }
}
