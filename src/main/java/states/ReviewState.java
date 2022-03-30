package states;

import orderfactory.Order;

public class ReviewState implements State {
    private Order order;

    public ReviewState(Order order) {
        this.order = order;
        notifyFreelancer(Messages.ACCEPTED.getString());
        notifyCustomer(Messages.FINISHED.getString());
    }

    @Override
    public void activeSearchOrder() {
        notifyCustomer(Messages.FINISHED.getString());
    }

    @Override
    public void makeReadyForReviewOrder() {
        notifyCustomer(Messages.FINISHED.getString());
    }

    @Override
    public void processOrder() {
        notifyCustomer(Messages.FINISHED.getString());
    }

    @Override
    public void finishOrder() {
        notifyCustomer(Messages.FINISHED.getString());
    }

    @Override
    public void cancelOrder() {
        notifyCustomer(Messages.FINISHED.getString());
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
