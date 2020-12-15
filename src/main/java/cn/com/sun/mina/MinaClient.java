package cn.com.sun.mina;

import org.apache.mina.core.future.ConnectFuture;
import org.apache.mina.transport.socket.SocketConnector;
import org.apache.mina.transport.socket.nio.NioSocketConnector;

/**
 * @author ：sunchao
 * @date ：Created in 2020/1/19 16:04
 * @description：Mina Client
 */
public class MinaClient {

    MinaClient() {
    }

    private void init() throws InterruptedException {

        SocketConnector connector = new NioSocketConnector();
        ConnectFuture future = connector.connect();
        future.await();

    }

    public static void main(String[] args) {

    }
}
