package states;

import orderfactory.Order;

public class ReadyForReviewState implements State {
    private Order order;

    public ReadyForReviewState(Order order) {
        this.order = order;
        notifyCustomer(Messages.REVIEW.getString());
    }

    @Override
    public void activeSearchOrder() {
        notifyCustomer(Messages.REVIEW.getString());
    }

    @Override
    public void makeReadyForReviewOrder() {
        notifyCustomer(Messages.REVIEW.getString());
    }

    @Override
    public void processOrder() {
        order.changeState(new FinishState(order));
    }

    @Override
    public void finishOrder() {
        notifyCustomer(Messages.REVIEW.getString());
    }

    @Override
    public void cancelOrder() {
        notifyCustomer(Messages.REVIEW.getString());
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
