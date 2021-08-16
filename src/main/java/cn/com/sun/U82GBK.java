package cn.com.sun;

import org.junit.jupiter.api.Test;

import java.io.*;

/**
 * @Description : U8 to gbk
 * @Author : mockingbird
 * @Date : 2021/4/1 13:04
 */
public class U82GBK {
    @Test
    public void doReadWriteTextFile() {
        String inputFileName = "D:\\repo\\KunShangNongShang_2021\\ab4x\\KunShanNongShang\\cn.com.agree.ab.a4.client.gui.adore.kunshan\\src\\cn\\com\\agree\\ab\\a4\\client\\gui\\adore\\kunshan\\device\\DevicePlugin.java";
        try {
            int bytesRead = 0;
            FileInputStream fis = new FileInputStream(inputFileName);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            OutputStreamWriter outputStream = new OutputStreamWriter(new FileOutputStream("D:\\oup"), "GBK");
            while ((bytesRead = isr.read()) != -1) {
                outputStream.write((char) bytesRead);
            }
            isr.close();
            outputStream.close();
            System.out.println("ok");
        } catch (IOException e) {
            System.out.println("IOException:");
        }
    }
}
