package com.nonono.test.delayQueue;

import java.util.concurrent.DelayQueue;

public class TestDelayQueue {

    private DelayQueue<Message> delayQueue = new DelayQueue();

    public void test() {
        delayQueue.put(new Message(3000, "test1"));
        delayQueue.put(new Message(1000, "test2"));
        delayQueue.put(new Message(5000, "test3"));
        try {
            while (!delayQueue.isEmpty()) {
                System.out.println(delayQueue.take().getData());
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("delayQueue test is end.");
    }

}
