package com.nonono.test.guavaTest;

import com.google.common.eventbus.Subscribe;

public class EventListener2 {
    public int lastMessage = 0;

    @Subscribe
    public void listen(EventMessageModel event) {
        lastMessage = event.getMessage();
        System.out.println("EventListener2 listen message:" + lastMessage);
    }

    @Subscribe
    public void listen(EventMessageModel2 event) {
        lastMessage = event.getMessage();
        System.out.println("EventListener2 listen message2:" + lastMessage);
    }
}
