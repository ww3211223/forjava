package com.nonono.test.guavaTest;

public class EventMessageModel2 {
    private final int message;

    public EventMessageModel2(int message) {
        this.message = message;
        System.out.println("event message2:" + message);
    }

    public int getMessage() {
        return message;
    }
}
