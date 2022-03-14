package states;

import orderfactory.Order;

public class ReadyForReviewState implements State {
    private Order order;

    public ReadyForReviewState(Order order) {
        this.order = order;
        notifyCustomer("is ready for review");
    }

    @Override
    public void activeSearchOrder() {
        notifyCustomer("is ready for review");
    }

    @Override
    public void makeReadyForReviewOrder() {
        notifyCustomer("is ready for review");
    }

    @Override
    public void processOrder() {
        order.changeState(new FinishState(order));
    }

    @Override
    public void finishOrder() {
        notifyCustomer("is ready for review");
    }

    @Override
    public void cancelOrder() {
        notifyCustomer("is ready for review");
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
