package server;

import orderfactory.Order;
import orderfactory.OrderFactory;

import java.util.ArrayList;

public class Customer implements Subscriber {
    private ArrayList<String> customerInfo;
    private OrderFactory orderFactory;
    private ArrayList<Order> orderList;
    private ArrayList<String> notifications;
    private boolean viewPermission;
    private String customerUsername;

    public Customer(OrderFactory orderFactory) {
        this.orderFactory = orderFactory;
    }

    public void createOrder(ArrayList<String> orderInfo) {

        orderList.add(orderFactory.createOrder(orderInfo, this));
    }

    public void choosingFreelancer(Order order) {
        order.getWaitingList();
    }


    @Override
    public void updateNotifications(String message) {
        notifications.add(message);
    }

    @Override
    public void cleanNotifications() {
        notifications.clear();
    }

    public ArrayList<String> getNotifications() {
        return notifications;
    }

    public ArrayList<Order> getOrderList() {
        return orderList;
    }

    public void setOrderList(ArrayList<Order> orderList) {
        this.orderList = orderList;
    }

    public ArrayList<String> getCustomerInfo() {
        return customerInfo;
    }

    public void setCustomerInfo(ArrayList<String> customerInfo) {
        this.customerInfo = customerInfo;
    }

    public OrderFactory getOrderFactory() {
        return orderFactory;
    }

    public void setOrderFactory(OrderFactory orderFactory) {
        this.orderFactory = orderFactory;
    }

    public String getCustomerUsername() {
        return customerUsername;
    }

    public void setCustomerUsername(String customerUsername) {
        this.customerUsername = customerUsername;
    }

    public boolean getViewPermission() {
        return viewPermission;
    }

    public void setViewPermission(boolean viewPermission) {
        this.viewPermission = viewPermission;
    }

}
