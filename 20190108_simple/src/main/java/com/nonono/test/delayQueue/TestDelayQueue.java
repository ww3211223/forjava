package com.nonono.test.delayQueue;

import java.time.LocalDateTime;
import java.util.concurrent.DelayQueue;

public class TestDelayQueue {

    private DelayQueue<Message> delayQueue = new DelayQueue();

    public void test() {
        delayQueue.put(new Message(3000, "test1"));
        delayQueue.put(new Message(1000, "test2"));
        delayQueue.put(new Message(8000, "test3"));

        System.out.println(LocalDateTime.now() + "|delayQueue start.");
        try {
            while (!delayQueue.isEmpty()) {
                Message mess = delayQueue.take();
                System.out.println(LocalDateTime.now() + "|" + mess.getData());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(LocalDateTime.now() + "|delayQueue end.");
    }

}
