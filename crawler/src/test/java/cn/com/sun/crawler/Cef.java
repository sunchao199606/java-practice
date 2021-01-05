package cn.com.sun.crawler;

import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.OS;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefLoadHandlerAdapter;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class Cef {
    private static final Logger logger = LoggerFactory.getLogger(Cef.class);

    @Test
    public void main() {
        JFrame jFrame = new JFrame();
        CefApp cefApp = CefApp.getInstance();
        CefClient cefClient = cefApp.createClient();
        cefClient.addLoadHandler(new CefLoadHandlerAdapter() {
            @Override
            public void onLoadEnd(CefBrowser cefBrowser, CefFrame cefFrame, int i) {
                cefBrowser.getSource(s -> logger.info("html : {}" + s));
            }
        });
        CefBrowser browser = cefClient.createBrowser("http://0728.91p50.com/view_video.php?viewkey=df7c3529588a578bc573", OS.isLinux(), false);

        jFrame.getContentPane().add(browser.getUIComponent(), BorderLayout.CENTER);
        jFrame.pack();
        jFrame.setSize(50, 50);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jFrame.setVisible(true);
    }
}
