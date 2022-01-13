package cn.com.sun.http;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.ServerConnector;
import org.eclipse.jetty.servlet.ServletContextHandler;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class HttpFileServer {

    private static void startServer() throws Exception {
        Server server = new Server();
        ServerConnector connector = new ServerConnector(server);
        connector.setPort(8085);
        server.addConnector(connector);

        ServletContextHandler contextHandler = new ServletContextHandler(ServletContextHandler.SESSIONS);
        contextHandler.setContextPath("/");
        contextHandler.addServlet(DownloadFileServlet.class, "/");
        server.setHandler(contextHandler);
        server.start();
    }

    public static void main(String[] args) throws Exception {
        startServer();
        download();
    }

    private static void download() throws IOException {
        URL url = new URL("http://10.10.1.28:8085");
        URLConnection connection = url.openConnection();
        connection.connect();
        try (InputStream inputStream = connection.getInputStream();
             FileOutputStream outputStream = new FileOutputStream("D://down.mp4")) {
            byte[] bytes = new byte[1024];
            for (; inputStream.read(bytes) != -1; ) {
                outputStream.write(bytes);
            }
        }

    }

}