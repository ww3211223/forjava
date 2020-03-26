package com.nonono.test.threadTest;

import java.time.LocalDateTime;

/**
 * 等待/通知机制测试
 */
public class WaitNotify {

    static boolean flag = true;
    static Object lock = new Object();

    public void test() {
        Thread waitThread = new Thread(new Wait(), "WaitThread");
        waitThread.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Thread notifyThread = new Thread(new Notify(), "NotifyThread");
        notifyThread.start();
    }

    static class Wait implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                while (flag) {
                    try {
                        System.out.println("[" + LocalDateTime.now() + "]" + "Wait类开始等待...");
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }

                System.out.println("[" + LocalDateTime.now() + "]" + "Wait类已完成.");
            }
        }
    }

    static class Notify implements Runnable {
        @Override
        public void run() {
            synchronized (lock) {
                System.out.println("[" + LocalDateTime.now() + "]" + "Notify已获得锁，准备进行通知。");
                lock.notifyAll();
                flag = false;
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            synchronized (lock) {
                System.out.println("[" + LocalDateTime.now() + "]" + "Notify再次获得锁。");
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
