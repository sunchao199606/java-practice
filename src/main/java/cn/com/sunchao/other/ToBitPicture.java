package cn.com.sunchao.other;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ToBitPicture {

	public static void main(String[] args) {

		BufferedImage img_jpg = null;
		BufferedImage img_1bit = new BufferedImage(200, 200, BufferedImage.TYPE_BYTE_BINARY); // 默认1bit
		Graphics g = img_1bit.getGraphics();
		g.drawImage(img_jpg, 0, 0, null);
		g.dispose();
		try {
			ImageIO.write(img_1bit, "BMP", new File("test.bmp"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
