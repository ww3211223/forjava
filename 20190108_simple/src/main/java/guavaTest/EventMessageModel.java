package guavaTest;

public class EventMessageModel {
    private final int message;

    public EventMessageModel(int message) {
        this.message = message;
        System.out.println("event message:" + message);
    }

    public int getMessage() {
        return message;
    }
}
