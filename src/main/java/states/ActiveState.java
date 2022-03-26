package states;

import orderfactory.Order;
import server.Burse;

public class ActiveState implements State {
    private Order order;

    public ActiveState(Order order) {
        this.order = order;
        notifyCustomer(Messages.ACTIVE.getString());
    }

    @Override
    public void activeSearchOrder() {
        notifyCustomer(Messages.ACTIVE.getString());
    }

    @Override
    public void makeReadyForReviewOrder() {
    }

    @Override
    public void processOrder() {
        Burse.getInstance().removeOrder(order);
        order.changeState(new FinishState(order));
    }


    @Override
    public void finishOrder() {
        notifyCustomer(Messages.ACTIVE.getString());
    }

    @Override
    public void cancelOrder() {
        Burse.getInstance().removeOrder(order);
        order.changeState(new CancelState(order));

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
