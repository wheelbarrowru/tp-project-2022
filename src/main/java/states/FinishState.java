package states;

import orderfactory.Order;

public class FinishState implements State {
    private Order order;

    public FinishState(Order order) {
        this.order = order;
        notifyCustomer("is in process");
        if (order.getState() instanceof ActiveState) {
            notifyFreelancer("your application has been approved");
        } else {
            notifyFreelancer("needs improvement");
        }
    }

    @Override
    public void activeSearchOrder() {
        notifyCustomer("is in process");
    }

    @Override
    public void processOrder() {
        notifyCustomer("is already in process");
    }

    @Override
    public void makeReadyForReviewOrder() {
        order.changeState(new ReadyForReviewState(order));
    }


    @Override
    public void finishOrder() {
        notifyCustomer("is in process");
    }

    @Override
    public void cancelOrder() {
        notifyCustomer("is in process");
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
