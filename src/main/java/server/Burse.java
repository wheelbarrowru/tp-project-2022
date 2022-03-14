package server;

import orderfactory.Order;

import java.util.ArrayList;

public class Burse {
    private ArrayList<Order> activeOrderList;
    private ArrayList<Order> archiveOrderList;
    private static Burse instance = new Burse();

    private Burse() {
    }

    public void addOrder(Order order) {
        if (!activeOrderList.contains(order)) {
            activeOrderList.add(order);
        }
        archiveOrderList.remove(order);
    }

    public void removeOrder(Order order) {
        if (!archiveOrderList.contains(order)) {
            archiveOrderList.add(order);
        }
        activeOrderList.remove(order);
    }

    public ArrayList<Order> getActiveOrderList() {
        return activeOrderList;
    }

    public static Burse getInstance() {
        return instance;
    }
}
