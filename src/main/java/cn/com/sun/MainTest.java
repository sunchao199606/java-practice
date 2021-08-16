package cn.com.sun;

import com.sun.org.apache.xerces.internal.impl.XMLEntityManager;
import com.sun.org.apache.xerces.internal.util.URI;
import org.eclipse.swt.internal.win32.OS;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Description :
 * @Author : Mockingbird
 * @Date : 2020-08-15 13:29
 */
public class MainTest {
    @Test
    public void stringTest(){
        String A = "abc";
        String B = "abc";
        System.out.println(A == B);
    }
    @Test
    public void testForEach() {
        List<Integer> list = IntStream.rangeClosed(1, 100).boxed().collect(Collectors.toList());
        for (Integer i : list) {
            if (i == 1) {
                list.remove(i);
            }
        }
    }

    @Test
    public void testWaitingOnCondition() throws InterruptedException {
        Thread.sleep(10000);
    }

    @Test
    public void testBlocked() throws InterruptedException {
        Object lock = new Object();
        AtomicReference<Thread> thread1 = new AtomicReference<>();
        AtomicReference<Thread> thread2 = new AtomicReference<>();

        Runnable runnable1 = () -> {
            synchronized (lock) {
                try {
                    lock.wait();
                    System.out.println(thread2.get().getState());
                    System.out.println(Thread.currentThread().getName() + "re enter");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Runnable runnable2 = () -> {
            try {
                synchronized (lock) {
                    lock.wait();
                    System.out.println(thread1.get().getState());
                    System.out.println(Thread.currentThread().getName() + "re enter");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        };
        thread1.set(new Thread(runnable1));
        thread2.set(new Thread(runnable2));
        thread1.get().start();
        thread2.get().start();
        Thread.sleep(100);
        synchronized (lock) {
            lock.notifyAll();
        }
        Thread.sleep(100000);
    }

    @Test
    public void testJFrame() throws InterruptedException {
        JFrame frame = new JFrame("Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(400, 300);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.addWindowListener(new WindowAdapter() {
            public void windowDeactivated(WindowEvent e) {
                frame.setVisible(true);
                System.out.println(frame.isActive());
            }
        });
        Thread.sleep(10000000);
    }

    @Test
    public void testOS() {
        OS.SetForegroundWindow(7605830);
        //Shell shell = OS.GetActiveWindow()
    }

    @Test
    public void testXmlEntityManager() throws URI.MalformedURIException {
        System.out.println(XMLEntityManager.expandSystemId("E:\\工作文件\\A徽商银行\\abc/resourceFolder/pdf/fop.xconf", null, false));
    }


    @Test
    public void testFloat() {
        float r = 7 / (float) 20;
        System.out.println(r);
    }

    @Test
    public void testVersion() {
        Version version = new Version("1.6.0");
        System.out.println(".jar".compareTo("202104032208") > 0);
    }

    @Test
    public void testVersionPattern() throws Exception {
        Pattern pattern = Pattern.compile("(.+)_(\\d+\\.\\d+(\\.\\d+(\\.[^.]+)?)?)\\.jar");
        // org.apache.xmlgraphics_2.3.0.202104032208.jar
        String name = "org.eclipse.core.resources.win32.x86_3.5.200.v20170516-0925.jar";
        Matcher m = pattern.matcher(name);
        // ignore other files
        if (!m.matches()) {
            throw new Exception("");
        }
        String id = m.group(1);
        String version = m.group(2);
        for (int i = 0; i < m.groupCount(); i++) {
            System.out.println(m.group(i));
        }
        System.out.println("id : " + id);
        System.out.println("version : " + version);
    }

    private String convert(String s) {
        BigDecimal bd = new BigDecimal(s);
        String decimalString = s.substring(s.indexOf(".") + 1);
        if (decimalString.matches("0+")) {
            if (decimalString.length() > 2)
                return bd.setScale(2).toPlainString();
            else
                return s;
        }
        return bd.stripTrailingZeros().toPlainString();
    }

    @Test
    public void testConvertString() {
        System.out.println(convert("1.01"));
    }

    @Test
    public void testAutoBox() {
        boxMethod(false);
        boxMethod(false);
    }

    private void boxMethod(Boolean yes) {
        System.out.println("box" + yes);
    }

    private void boxMethod(boolean yes) {
        System.out.println(yes);
    }

}

class Version {
    public int major, minor, service;

    public String stamp = "";

    public Version(String str) {
        // parse major
        int majorDot = str.indexOf('.');
        if (majorDot < 0) {
            major = Integer.parseInt(str);
            return;
        }
        major = Integer.parseInt(str.substring(0, majorDot));
        // parse minor
        int minorDot = str.indexOf('.', majorDot + 1);
        if (minorDot < 0) {
            minor = Integer.parseInt(str.substring(majorDot + 1));
            return;
        }
        minor = Integer.parseInt(str.substring(majorDot + 1, minorDot));
        // parse service
        int serviceDot = str.indexOf('.', minorDot + 1);
        if (serviceDot < 0) {
            service = Integer.parseInt(str.substring(minorDot + 1));
            return;
        }
        service = Integer.parseInt(str.substring(minorDot + 1, serviceDot));
        stamp = str.substring(serviceDot + 1);
    }
}
