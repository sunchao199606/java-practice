package cn.com.sunchao.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 提供文件与byte[]的MD5运算
 * 
 */
public class MD5Util {
	private MD5Util() {
	}

	public static String md5(File file) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// impossible
			return null;
		}
		byte[] buf = new byte[4096];
		int len;
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
			// 使用流式计算资源文件md5值，避免计算大文件时OOM
			while ((len = bis.read(buf)) > 0) {
				md.update(buf, 0, len);
			}
		} catch (IOException e) {
			return null;
		}
		byte[] result = md.digest();
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < result.length; i++) {
			String s = Integer.toHexString(0xff & result[i]);
			if (s.length() < 2) {
				sb.append('0');
			}
			sb.append(s);
		}
		return sb.toString();
	}

	public static String md5(byte[] content) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// impossible
			return null;
		}
		byte[] result = md.digest(content);
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < result.length; i++) {
			String s = Integer.toHexString(0xff & result[i]);
			if (s.length() < 2) {
				sb.append('0');
			}
			sb.append(s);
		}
		return sb.toString();
	}

	public static byte[] md5Byte(File file) {
		MessageDigest md;
		try {
			md = MessageDigest.getInstance("MD5");
		} catch (NoSuchAlgorithmException e) {
			// impossible
			return null;
		}
		byte[] buf = new byte[4096];
		int len;
		try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(file));) {
			// 使用流式计算资源文件md5值，避免计算大文件时OOM
			while ((len = bis.read(buf)) > 0) {
				md.update(buf, 0, len);
			}
		} catch (IOException e) {
			return null;
		}
		return md.digest();
	}

	public static void main(String[] args) {
		String md51 = MD5Util.md5(new File(
				"E:\\89754\\weixin\\WeChat Files\\s_cgogogo\\FileStorage\\File\\2019-09\\cn.com.agree.ab.a4.client.adore.aft_4.0.0.201908221056.jar"));
	    String md52 = MD5Util.md5(new File(
				"E:\\work\\插件导出\\200\\plugins\\cn.com.agree.ab.a4.client.adore.aft_4.0.0.201909192157.jar"));
		System.out.println(md51);
	    System.out.println(md52);
	}
}
