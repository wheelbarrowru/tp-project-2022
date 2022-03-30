package states;

import orderfactory.Order;
import server.Burse;

public class CancelState implements State {
    private Order order;

    public CancelState(Order order) {
        this.order = order;
        Burse.getInstance().removeOrder(order);
        notifyCustomer(Messages.CANCELED.getString());
    }

    @Override
    public void activeSearchOrder() {
        Burse.getInstance().addOrder(order);
        order.changeState(new ActiveState(order));
    }

    @Override
    public void makeReadyForReviewOrder() {
        notifyCustomer(Messages.CANCELED.getString());
    }

    @Override
    public void processOrder() {
        notifyCustomer(Messages.CANCELED.getString());
    }


    @Override
    public void finishOrder() {
        notifyCustomer(Messages.CANCELED.getString());
    }

    @Override
    public void cancelOrder() {
        notifyCustomer(Messages.CANCELED.getString());
    }

    @Override
    public void notifyCustomer(String stateMessage) {
        order.getOrderCustomer().updateNotifications(String.format("Your order \"%s\" %s.",
                order.getOrderInfo().get(0), stateMessage));
    }

    @Override
    public void notifyFreelancer(String stateMessage) {

    }
}
