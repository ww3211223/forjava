package com.nonono.test.concurrentTest;

/**
 * http服务器测试
 */
public class HttpServerTest {

    public static void test() {
        SimpleHttpServer.setPort(9980);
        SimpleHttpServer.setBasePath("E:\\Work2\\forjava\\20190108_simple\\src\\main\\resources");
        try {
            SimpleHttpServer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
