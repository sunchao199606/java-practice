package cn.com.sunchao.util;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 用于流读写操作
 */
public class IOUtil {
	private IOUtil() {
	}

	public static void copy(InputStream in, OutputStream out)
			throws IOException {
		copy(in, out, true, true);
	}

	public static void copy(InputStream in, OutputStream out, boolean closeIn,
			boolean closeOut) throws IOException {
		try {
			byte[] buffer = new byte[4096];
			int length = 0;
			while ((length = in.read(buffer)) > 0) {
				out.write(buffer, 0, length);
			}
			out.flush();
		} finally {
			try {
				if (closeIn) {
					in.close();
				}
			} finally {
				if (closeOut) {
					out.close();
				}
			}
		}
	}
}
