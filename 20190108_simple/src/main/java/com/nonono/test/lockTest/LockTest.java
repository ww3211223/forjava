package com.nonono.test.lockTest;

import java.time.LocalDateTime;

public class LockTest {

    public void test() {
        testMutex();
    }

    private void testMutex() {
        Mutex mutex = new Mutex();
        for (int i = 0; i < 4; i++) {
            Thread thread = new Thread(() -> {
                mutex.lock();
                System.out.println(String.format("线程#%s 当前时间：%s 已获得锁。", Thread.currentThread().getName(), LocalDateTime.now()));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                System.out.println(String.format("线程#%s 当前时间：%s 准备释放锁。", Thread.currentThread().getName(), LocalDateTime.now()));
                mutex.unlock();
            }, "thread-" + i);

            thread.start();
        }
    }

}
