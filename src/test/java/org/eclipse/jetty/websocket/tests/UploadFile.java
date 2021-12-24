package org.eclipse.jetty.websocket.tests;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.websocket.client.WebSocketClient;
import org.junit.jupiter.api.Test;

public class UploadFile {

    private Server server;

    @Test
    public void startServer() throws Exception {
        server = new Server();
        ServerConnector connector = new ServerConnector(server, 10, 5);
        connector.setPort(8080);
        //connector.setConnectionFactories();
        server.addConnector(connector);
        server.start();
    }

    @Test
    public void startClient() {
        WebSocketClient client = new WebSocketClient();
        //client.connect()
    }
}
