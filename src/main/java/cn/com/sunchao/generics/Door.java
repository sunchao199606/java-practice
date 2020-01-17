package cn.com.sunchao.generics;

import java.io.Closeable;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author ：sunchao
 * @date ：Created in 2020/1/17 17:45
 * @description：
 */
public class Door extends ArrayList implements Closeable {
    @Override
    public void close() throws IOException {
    }
}
