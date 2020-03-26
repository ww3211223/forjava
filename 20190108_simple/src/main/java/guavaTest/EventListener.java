package guavaTest;

import com.google.common.eventbus.Subscribe;

public class EventListener {
    public int lastMessage = 0;

    @Subscribe
    public void listen(EventMessageModel event) {
        lastMessage = event.getMessage();
        System.out.println("EventListener listen message:" + lastMessage);
    }

    public int getLastMessage() {
        return lastMessage;
    }
}
