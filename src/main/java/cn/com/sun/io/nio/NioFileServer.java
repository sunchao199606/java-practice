package cn.com.sun.io.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.util.Set;

public class NioFileServer {

    public static final String fileStorage = "D:\\NioFileServerStorage";

    public static void main(String[] args) throws IOException {
        // 打开Selector
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(8888));
        // 注册筛选ServerChannel
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    if (selector.select() == 0)
                        continue;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                for (SelectionKey key : selectionKeys) {
                    if (key.isAcceptable()) {
                        System.out.println("server is ready");
                    }
                }
            }
        });
        thread.start();
    }
}
