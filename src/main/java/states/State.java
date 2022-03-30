package states;

public interface State {
    void activeSearchOrder();

    void processOrder();

    void makeReadyForReviewOrder();

    void finishOrder();

    void cancelOrder();

    void notifyCustomer(String stateMessage);

    void notifyFreelancer(String stateMessage);

}
