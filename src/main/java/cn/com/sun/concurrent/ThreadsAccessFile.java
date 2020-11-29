package cn.com.sun.concurrent;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import cn.com.sun.util.IOUtil;
import cn.com.sun.util.MD5Util;

public class ThreadsAccessFile {

	public static void main(String[] args) {
		File file = new File("C:\\Users\\89754\\Desktop\\stamp\\test");
		File stampFile = new File("C:\\Users\\89754\\Desktop\\stamp\\.stamp");
		List<String> strList = new ArrayList<>();
		// AtomicReference<String> content = new AtomicReference<>();
		Runnable accessFile = () -> {
			if (file.exists() && stampFile.lastModified() == file.lastModified()) {
				try (FileInputStream fis = new FileInputStream(file);
						ByteArrayOutputStream baos = new ByteArrayOutputStream()) {
					IOUtil.copy(fis, baos);
					strList.add(baos.toString("utf8"));
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			String stamp = MD5Util.md5(file);
			try {
				IOUtil.copy(new ByteArrayInputStream(stamp.getBytes("utf8")), new FileOutputStream(stampFile));
			} catch (IOException e) {
				e.printStackTrace();
			}
		};

		for (int i = 0; i < 1000; i++) {
			Thread thread = new Thread(accessFile);
			thread.start();
		}

		try {
			Thread.currentThread().join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		for (String string : strList) {
			System.out.println(string);
		}
	}
}
