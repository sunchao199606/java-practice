package cn.com.sun.j2v8;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.NodeJS;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileOutputStream;

public class NodeJs {
    static String NODE_SCRIPT = "var http = require('http');\n"
            + ""
            + "var server = http.createServer(function (request, response) {\n"
            + " response.writeHead(200, {'Content-Type': 'text/plain'});\n"
            + " response.end(someJavaMethod());\n"
            + "});\n"
            + ""
            + "server.listen(8010);\n"
            + "console.log('Server running at https://localhost:8010/');";

    public static void main(String[] args) {
        final NodeJS nodeJS = NodeJS.createNodeJS();
        JavaCallback callback = (receiver, parameters) -> "Hello, JavaWorld!";

        nodeJS.getRuntime().registerJavaMethod(callback, "someJavaMethod");
        File nodeScript = createTemporaryScriptFile(NODE_SCRIPT, "example");

        nodeJS.exec(nodeScript);

        while (nodeJS.isRunning()) {
            nodeJS.handleMessage();
        }
        nodeJS.release();
    }

    private static File createTemporaryScriptFile(String nodeScript, String example) {
        File file = new File(example);
        try (ByteArrayInputStream is = new ByteArrayInputStream(nodeScript.getBytes());
             FileOutputStream fos = new FileOutputStream(file)) {
            byte[] buf = new byte[1024];
            int length;
            while ((length = is.read(buf, 0, buf.length)) != -1) {
                fos.write(buf, 0, length);
            }
            fos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return file;
    }
}
