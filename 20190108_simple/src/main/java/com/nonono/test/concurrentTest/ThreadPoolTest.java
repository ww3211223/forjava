package com.nonono.test.concurrentTest;

/**
 * 简单线程池测试
 */
public class ThreadPoolTest {

    public void test() {
        int poolSize = 3;
        ThreadPool pool = new DefaultThreadPool(poolSize);
        for (int i = 0; i < 100; i++) {
            int finalI = i;
            pool.execute(() -> {
                System.out.println("当前线程：" + Thread.currentThread().getName() + "，第" + finalI + "个任务在执行。");
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    System.out.println("线程被中断。");
                }
            });
        }

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
//        //关闭线程
//        pool.shutdown();
//        System.out.println("关闭线程完毕。");

        //减少线程数
        pool.removeWorker(2);
        System.out.println("减少线程数完毕，当前待执行的任务数量：" + pool.getJobSize());

        //增加线程数
        pool.addWorkers(5);
        System.out.println("增加线程数完毕，当前待执行的任务数量：" + pool.getJobSize());
    }
}
