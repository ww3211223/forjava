package com.nonono.test.threadTest;

import java.time.LocalDateTime;
import java.util.concurrent.*;

/**
 * Future测试
 */
public class FutureTest {
    private ExecutorService testThreadPool = Executors.newFixedThreadPool(3);

    public void testFuture() throws ExecutionException, InterruptedException {
        Callable<String> call = new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(3000);
                System.out.println("-------work end: " + LocalDateTime.now().toString());
                return "do work.";
            }
        };

        Future<String> task = testThreadPool.submit(call);
        System.out.println("----sleep begin: " + LocalDateTime.now().toString());
        Thread.sleep(5000);
        System.out.println("------sleep end: " + LocalDateTime.now().toString());

        String result = task.get();
        System.out.println("-------task end: " + LocalDateTime.now().toString());
        System.out.println("---------result: " + result);
    }
}
