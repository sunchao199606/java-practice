package cn.com.sun.java8;
import java.io.DataInputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class IODemo {

	public static void main(String[] args) {
		try (Socket socket = new Socket();
				DataInputStream dataInputStream = new DataInputStream(socket.getInputStream())) {
			socket.connect(new InetSocketAddress(22));
		} catch (Exception exception) {
			return;
		}
	}
}
