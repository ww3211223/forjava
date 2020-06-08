package com.nonono.test.concurrentTest;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 简单的http服务器
 */
public class SimpleHttpServer {
    //处理http请求线程池
    static ThreadPool<HttpRequestHandler> threadPool = new DefaultThreadPool<HttpRequestHandler>(1);
    //SimpleHttpServer根目录
    static String basePath;
    static ServerSocket serverSocket;
    //监听端口
    static int port = 9980;

    public static void setPort(int port) {
        if (port > 0) {
            SimpleHttpServer.port = port;
        }
    }

    public static void setBasePath(String basePath) {
        File file = new File(basePath);
        if (basePath != null && file.exists() && file.isDirectory()) {
            SimpleHttpServer.basePath = basePath;
        }
    }

    /**
     * 启动SimpleHttpServer
     *
     * @throws Exception
     */
    public static void start() throws Exception {
        serverSocket = new ServerSocket(port);
        Socket socket = null;
        System.out.println(String.format("httpServer is starting. port#%s", port));
        while ((socket = serverSocket.accept()) != null) {
            //接收一个客户端Socket，生成一个HttpRequestHandler，放入线程池执行
            threadPool.execute(new HttpRequestHandler(socket));
        }
    }


    /**
     * Http请求处理器
     */
    static class HttpRequestHandler implements Runnable {
        private Socket socket;

        public HttpRequestHandler(Socket socket) {
            this.socket = socket;
        }

        @Override
        public void run() {
            String line = null;
            BufferedReader fileReader = null;
            BufferedReader socketReader = null;
            PrintWriter out = null;
            InputStream in = null;
            try {
                socketReader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String header = socketReader.readLine();
                //由相对路径计算出绝对路径
                String filePath = basePath + header.split(" ")[1];
                out = new PrintWriter(socket.getOutputStream());
                //如果请求资源的后缀为jpg或者ico，则读取资源并输出
                if (filePath.endsWith("jpg") || filePath.endsWith("ico") || filePath.endsWith("png")) {
                    in = new FileInputStream(filePath);
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    int i = 0;
                    while ((i = in.read()) != -1) {
                        baos.write(i);
                    }
                    byte[] array = baos.toByteArray();
                    out.println("HTTP/1.1 200 OK");
                    out.println("Server: Molly");
                    out.println("Content-Type: image/jpeg");
                    out.println("Content-Length: " + array.length);
                    out.println("");
                    out.flush();

                    socket.getOutputStream().write(array, 0, array.length);
                } else {
                    fileReader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)));
                    out = new PrintWriter(socket.getOutputStream());
                    out.println("HTTP/1.1 200 OK");
                    out.println("Server: Molly");
                    out.println("Content-Type: text/html; charset=UTF-8");
                    out.println("");
                    while ((line = fileReader.readLine()) != null) {
                        out.println(line);
                    }

                    out.flush();
                }
            } catch (Exception e) {
                out.println("HTTP/1.1 500");
                out.println("");
                out.flush();

                System.out.println(String.format("处理Http请求异常，异常信息：%s", e.getMessage()));
            } finally {
                close(fileReader, in, socketReader, out, socket);
            }
        }
    }

    /**
     * 关闭流或者Socket
     *
     * @param closeables
     */
    private static void close(Closeable... closeables) {
        if (closeables != null) {
            for (Closeable closeable : closeables) {
                try {
                    closeable.close();
                } catch (Exception ex) {
                }
            }
        }
    }
}
