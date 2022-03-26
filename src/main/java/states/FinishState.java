package states;

import orderfactory.Order;

public class FinishState implements State {
    private Order order;

    public FinishState(Order order) {
        this.order = order;
        notifyCustomer("is in process");
        if (order.getState() instanceof ActiveState) {
            notifyFreelancer(Messages.APPROVED.getString());
        } else {
            notifyFreelancer(Messages.IMPROVEMENT.getString());
        }
    }

    @Override
    public void activeSearchOrder() {
        notifyCustomer(Messages.PROCESS.getString());
    }

    @Override
    public void processOrder() {
        notifyCustomer(Messages.PROCESS.getString());
    }

    @Override
    public void makeReadyForReviewOrder() {
        order.changeState(new ReadyForReviewState(order));
    }


    @Override
    public void finishOrder() {
        notifyCustomer(Messages.PROCESS.getString());
    }

    @Override
    public void cancelOrder() {
        notifyCustomer(Messages.PROCESS.getString());
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
