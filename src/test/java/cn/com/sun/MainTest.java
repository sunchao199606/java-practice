package cn.com.sun;

import com.sun.org.apache.xerces.internal.impl.XMLEntityManager;
import com.sun.org.apache.xerces.internal.util.URI;
import org.eclipse.swt.internal.win32.OS;
import org.junit.jupiter.api.Test;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * @Description :
 * @Author : Mockingbird
 * @Date : 2020-08-15 13:29
 */
public class MainTest {
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

}
