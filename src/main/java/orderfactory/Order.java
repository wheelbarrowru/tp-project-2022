package orderfactory;

import server.Customer;
import server.Freelancer;
import states.ActiveState;
import states.State;

import java.util.ArrayList;

public abstract class Order {
    private State state = new ActiveState(this);
    private ArrayList<String> orderInfo;
    protected Customer orderCustomer;
    protected Freelancer orderFreelancer;
    private ArrayList<Freelancer> waitingList;

    public abstract ArrayList<Order> getCustomerHistoryInfo();


    public ArrayList<String> getCustomerInfo() {
        return orderCustomer.getCustomerInfo();
    }


    public void changeState(State state) {
        this.state = state;
    }

    public void activeSearchOrder() {
        state.activeSearchOrder();
    }

    public void processOrder(String message) {
        state.processOrder();
        if (!message.isEmpty()) {
            orderFreelancer.updateNotifications(String.format("Order \"%s\". Message from customer: %s.",
                    orderInfo.get(0), message));
        }
    }

    public void readyForReview(String message) {
        state.makeReadyForReviewOrder();
        orderCustomer.updateNotifications(String.format("Order \"%s\". Message from freelancer: %s.",
                orderInfo.get(0), message));
    }

    public void finishOrder() {
        state.finishOrder();
    }

    public void cancelOrder() {
        state.cancelOrder();
    }

    public void addToWaitingList(Freelancer freelancer) {
        waitingList.add(freelancer);
    }

    public ArrayList<Freelancer> getWaitingList() {
        return waitingList;
    }

    public ArrayList<String> getOrderInfo() {
        return orderInfo;
    }

    public void setOrderInfo(ArrayList<String> orderInfo) {
        this.orderInfo = orderInfo;
    }

    public Customer getOrderCustomer() {
        return orderCustomer;
    }

    public void setOrderCustomer(Customer orderCustomer) {
        this.orderCustomer = orderCustomer;
    }

    public Freelancer getOrderFreelancer() {
        return orderFreelancer;
    }

    public void setOrderFreelancer(Freelancer orderFreelancer) {
        this.orderFreelancer = orderFreelancer;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
}
