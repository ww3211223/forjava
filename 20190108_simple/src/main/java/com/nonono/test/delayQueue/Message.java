package com.nonono.test.delayQueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Message implements Delayed {

    private int time;

    private String data;

    private final long now() {
        return System.nanoTime();
    }

    public int getTime() {
        return this.time;
    }

    public String getData() {
        return this.data;
    }

    public Message(int time, String data) {
        this.time = time;
        this.data = data;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(time * 1000000000 - now(), TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        if (o == this) {
            return 0;
        }
        if (o instanceof Message) {
            Message x = (Message) o;
            long diff = time - x.getTime();
            if (diff < 0) {
                return -1;
            } else {
                return 1;
            }
        }

        long diff = getDelay(TimeUnit.NANOSECONDS) - o.getDelay(TimeUnit.NANOSECONDS);
        return (diff < 0) ? -1 : (diff > 0) ? 1 : 0;
    }

}
