package cn.com.sun.http;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

public class DownloadFileServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        File file = new File("D://116.mp4");
        long pos = RangeUtil.headerSetting(file, req, resp);

        byte[] buff = new byte[4096];
        try (OutputStream os = resp.getOutputStream();
             BufferedOutputStream out = new BufferedOutputStream(os);
             RandomAccessFile raf = new RandomAccessFile(file, "r");) {
            raf.seek(pos);
            int n = 0;
            while ((n = raf.read(buff)) != -1) {
                out.write(buff, 0, n);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
        resp.flushBuffer();
    }
}
