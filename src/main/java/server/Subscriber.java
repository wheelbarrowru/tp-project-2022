package server;

public interface Subscriber {
    void updateNotifications(String message);

    void cleanNotifications();
}
