package guavaTest;

import com.google.common.eventbus.EventBus;

public class EventBusTest {

    public void test() {
        EventBus eventBus = new EventBus("test");
        EventListener listener = new EventListener();
        eventBus.register(listener);

        eventBus.post(new EventMessageModel(200));
        eventBus.post(new EventMessageModel(300));
        eventBus.post(new EventMessageModel(400));

        System.out.println("TestEventBus lastMessage:" + listener.getLastMessage());
    }
}
