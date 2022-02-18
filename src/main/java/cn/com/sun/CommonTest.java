package cn.com.sun;

import org.junit.jupiter.api.Test;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

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
    public void testSortMap() {
        List<HashMap<Integer,String>> list = new ArrayList<>();
//        Collections.sort(list,(m1,m2) -> {
//            m1.get()
//        });
    }

}
