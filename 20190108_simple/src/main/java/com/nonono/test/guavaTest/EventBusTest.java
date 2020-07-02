package com.nonono.test.guavaTest;

import com.google.common.eventbus.EventBus;

public class EventBusTest {

    public void test() {
        EventBus eventBus = new EventBus("test");
        EventListener listener = new EventListener();
        EventListener2 listener2 = new EventListener2();
        eventBus.register(listener);
        eventBus.register(listener2);

        eventBus.post(new EventMessageModel(200));
        eventBus.post(new EventMessageModel(300));
        eventBus.post(new EventMessageModel(400));

        eventBus.post(new EventMessageModel2(1000));
        eventBus.post(new EventMessageModel2(2000));
        eventBus.post(new EventMessageModel2(3000));

        System.out.println("TestEventBus lastMessage:" + listener.getLastMessage());
    }
}
