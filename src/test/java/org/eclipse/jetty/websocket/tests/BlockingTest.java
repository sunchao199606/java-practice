package org.eclipse.jetty.websocket.tests;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.WriteCallback;
import org.eclipse.jetty.websocket.api.util.WSURI;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.eclipse.jetty.websocket.common.WebSocketSession;
import org.eclipse.jetty.websocket.servlet.WebSocketServlet;
import org.eclipse.jetty.websocket.servlet.WebSocketServletFactory;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.nio.ByteBuffer;
import java.util.Arrays;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class BlockingTest {

    private Server server;
    private WebSocketClient client;

    @BeforeEach
    public void startClient() throws Exception {
        client = new WebSocketClient();
        client.start();
    }

    @BeforeEach
    public void startServer() throws Exception {
        server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8888);
        server.addConnector(connector);
        ServletContextHandler contextHandler = new ServletContextHandler();
        contextHandler.setContextPath("/");
        WebSocketServlet servlet = new WebSocketServlet() {
            @Override
            public void configure(WebSocketServletFactory factory) {
                factory.register(EventSocket.class);
            }
        };

        contextHandler.addServlet(new ServletHolder(servlet), "/");
        server.setHandler(contextHandler);
        server.start();
    }

    @Test
    public void testBlocking() throws Exception {
        CloseTrackingEndpoint cliSock = new CloseTrackingEndpoint();
        client.getPolicy().setIdleTimeout(10000);
        URI wsUri = WSURI.toWebsocket(server.getURI());
        Future<Session> future = client.connect(cliSock, wsUri);
        WebSocketSession session = (WebSocketSession) future.get(3000, TimeUnit.SECONDS);
        byte[] data = new byte[1024 * 10];
        Arrays.fill(data, (byte) 10);
        ByteBuffer byteBuffer = ByteBuffer.wrap(data);
        for (int i = 0; i < 100; i++) {
            new Thread(() -> {
                try {
                    WriteCallback callback = new WriteCallback() {
                        @Override
                        public void writeFailed(Throwable x) {
                        }

                        @Override
                        public void writeSuccess() {
                            System.out.println(System.currentTimeMillis() + " end success");
                        }
                    };
                    session.getRemote().sendBytes(byteBuffer, callback);
//                    Future<Void> f = session.getRemote().sendBytesByFuture(byteBuffer);
//                    f.get(5, TimeUnit.SECONDS);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }).start();
        }
        Thread.sleep(2000);
    }

    @AfterEach
    public void stopClient() throws Exception {
        client.stop();
    }

    @AfterEach
    public void stopServer() throws Exception {
        server.stop();
    }
}
