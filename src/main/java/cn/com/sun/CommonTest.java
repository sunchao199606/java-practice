package cn.com.sun;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class CommonTest {

    @Test
    public void testFileNotFound(String[] args) {
        try {
            InputStream inputStream = new FileInputStream("C:\\Windows\\System32\\accessibilitycpl.dll");
            inputStream.read();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @Test
    public void huzi(int a, int b) {

    }

}
