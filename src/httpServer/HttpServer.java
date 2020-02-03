package httpServer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Author Honghan Zhu
 */
public class HttpServer {
    static ExecutorService threadPool = Executors.newCachedThreadPool();
    static String basePath = "D:/JavaWorkspace/ConcurrentProgramming/src/httpServer/serverPath";
    static ServerSocket serverSocket;
    static int port = 8080;

    public static void startServer() throws Exception {
        serverSocket = new ServerSocket(port);
        Socket socket = null;
        while ((socket = serverSocket.accept()) != null) {
            threadPool.execute(new HttpRequestHandler(socket));
        }
        serverSocket.close();
    }

    static class HttpRequestHandler implements Runnable {
        private Socket socket;

        public HttpRequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            BufferedReader br = null;
            BufferedReader reader = null;
            PrintWriter out = null;
            InputStream in = null;
            String line = null;
            try {
                reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String header = reader.readLine();
                String filePath = basePath + header.split(" ")[1];
                System.out.println(header);
                out = new PrintWriter(socket.getOutputStream());
                if (filePath.endsWith(".jpg") || filePath.endsWith("ico")) {
                    System.out.println(filePath);
                    in = new FileInputStream(filePath);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int read = 0;
                    while ((read = in.read()) != -1) {
                        baos.write(read);
                    }
                    byte[] bytes = baos.toByteArray();
                    out.println("HTTP/1.1 200 OK");
                    out.println("Server: Molly");
                    out.println("Content-Type: image/jpeg");
                    out.println("Content-Length: " + bytes.length);
                    out.println("");
                    out.flush();
                    socket.getOutputStream().write(bytes, 0, bytes.length);
                } else {
                    System.out.println(filePath);
                    br = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
                    out.println("HTTP/1.1 200 OK");
                    out.println("Server: Molly");
                    out.println("Content-Type: text/html; charset=UTF-8");
                    out.println("");
                    while ((line = br.readLine()) != null) {
                        out.println(line);
                    }
                    out.flush();
                }
            } catch (IOException e) {
                out.println("HTTP/1.1 500");
                out.println("");
                out.flush();
            } finally {

            }
        }

        private static void close(Closeable... closeables) throws IOException {
            if (closeables != null) {
                for (Closeable closeable : closeables) {
                    closeable.close();
                }
            }
        }
    }
}
