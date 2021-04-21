package cn.com.sun.encode;

import java.io.*;

/**
 * @Description : U8 to gbk
 * @Author : mockingbird
 * @Date : 2021/4/1 13:04
 */
public class U82GBK {
    private static void doReadWriteTextFile(String inputFileName, String outputFileName) {
        try {
            int bytesRead = 0;
            FileInputStream fis = new FileInputStream(inputFileName);
            InputStreamReader isr = new InputStreamReader(fis, "UTF-8");
            OutputStreamWriter outputStream = new OutputStreamWriter(new FileOutputStream(outputFileName), "GBK");
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
