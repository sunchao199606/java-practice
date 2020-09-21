package cn.com.sunchao;

import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.platform.win32.WinUser;
import org.junit.jupiter.api.Test;

/**
 * @Description : JNA操作计算机本地接口测试
 * @Author : mockingbird
 * @Date : 2020/9/7 9:51
 */
public class JnaTest {

    /**
     * 闪烁窗体
     */
    @Test
    public void testFlashWindowEx() {
        User32 user32 = User32.INSTANCE;
        final WinUser.FLASHWINFO info = new WinUser.FLASHWINFO();
        info.cbSize = info.size();
        WinNT.HANDLE handle = new WinNT.HANDLE(Pointer.createConstant(467004));
        info.hWnd = handle;
        info.uCount = 5;
        info.dwFlags = User32.FLASHW_ALL;
        info.dwTimeout = 10;
//        flashwinfo.cbSize = 20;
//        flashwinfo.dwFlags = 0x00000002/*FLASHW_TRAY*/|0x0000000C/*FLASHW_TIMERNOFG*/;
        user32.FlashWindowEx(info);
    }

    /**
     * 获取前景窗口
     */
    @Test
    public void testGetForegroundWindow() {
        User32 user32 = User32.INSTANCE;
        System.out.println(Long.toHexString(Pointer.nativeValue(user32.GetForegroundWindow().getPointer())));
    }
}
