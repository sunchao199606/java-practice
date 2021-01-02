package cn.com.sun.crawler;

import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.OS;
import org.cef.browser.CefBrowser;
import org.cef.browser.CefFrame;
import org.cef.handler.CefLoadHandlerAdapter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.awt.*;

public class Cef {
    private static final Logger logger = LoggerFactory.getLogger(Cef.class);
    private static boolean min = false;

    public static void main(String[] args) {
        JFrame jFrame = new JFrame();
        CefApp cefApp = CefApp.getInstance();
        CefClient cefClient = cefApp.createClient();
        cefClient.addLoadHandler(new CefLoadHandlerAdapter() {
            @Override
            public void onLoadEnd(CefBrowser cefBrowser, CefFrame cefFrame, int i) {
                cefBrowser.getSource(s -> logger.info("html : {}" + s));
                if (!min) {
                    jFrame.setExtendedState(Frame.ICONIFIED);
                    min = true;
                }
            }
        });
        CefBrowser browser = cefClient.createBrowser("https://www.baidu.com/", OS.isLinux(), false);

        jFrame.getContentPane().add(browser.getUIComponent(), BorderLayout.CENTER);
        jFrame.pack();
        jFrame.setSize(50, 50);
        jFrame.setResizable(false);
        jFrame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        jFrame.setVisible(true);
    }
}
