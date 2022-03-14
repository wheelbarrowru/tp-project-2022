package states;

import orderfactory.Order;

public class ReviewState implements State {
    private Order order;

    public ReviewState(Order order) {
        this.order = order;
        notifyFreelancer("work accepted");
        notifyCustomer("is finished");
    }

    @Override
    public void activeSearchOrder() {
        notifyCustomer("is finished");
    }

    @Override
    public void makeReadyForReviewOrder() {
        notifyCustomer("is finished");
    }

    @Override
    public void processOrder() {
        notifyCustomer("is finished");
    }

    @Override
    public void finishOrder() {
        notifyCustomer("is finished");
    }

    @Override
    public void cancelOrder() {
        notifyCustomer("is finished");
    }

    @Override
    public void notifyCustomer(String stateMessage) {
        order.getOrderCustomer().updateNotifications(String.format("Your order \"%s\" %s.",
                order.getOrderInfo().get(0), stateMessage));
    }

    @Override
    public void notifyFreelancer(String stateMessage) {
        order.getOrderFreelancer().updateNotifications(String.format("Order \"%s\": %s.",
                order.getOrderInfo().get(0), stateMessage));
    }
}
