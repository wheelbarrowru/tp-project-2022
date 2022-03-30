package server;

import orderfactory.Order;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

public class Freelancer implements Subscriber {
    public static final String[] freelancerFields = {"name", "occupation", "experience", "summary", "contacts"};
    private ArrayList<String> freelancerInfo;
    private ArrayList<Order> bidList;
    private ArrayList<Order> activeOrderList;
    private ArrayList<String> notifications;
    private String freelancerUsername;

    public Freelancer(Map<String, String> freelancerInfoMap) {
        var fieldsArray = new String[5];
        for (Map.Entry<String, String> field : freelancerInfoMap.entrySet()) {
            switch (field.getKey()) {
                case "companyName" -> fieldsArray[0] = field.getValue();
                case "occupation" -> fieldsArray[1] = field.getValue();
                case "experience" -> fieldsArray[2] = field.getValue();
                case "summary" -> fieldsArray[3] = field.getValue();
                case "contacts" -> fieldsArray[4] = field.getValue();
                case "username" -> this.setFreelancerUsername(field.getValue());
            }
        }
        this.setFreelancerInfo(new ArrayList<>(Arrays.asList(fieldsArray)));
    }

    public Optional<Order> searchNewOrder() {
        Burse.getInstance().getActiveOrderList();
        return null;
    }

    public void cancelBid(Order order) {
        order.getWaitingList().remove(this);
    }


    public ArrayList<Order> viewCustomerProfile(Order order) {
        return order.getCustomerHistoryInfo();
    }

    @Override
    public void updateNotifications(String message) {
        notifications.add(message);
    }

    @Override
    public void cleanNotifications() {
        notifications.clear();
    }

    public ArrayList<Order> getActiveOrderList() {
        return activeOrderList;
    }

    public void setActiveOrderList(ArrayList<Order> activeOrderList) {
        this.activeOrderList = activeOrderList;
    }

    public ArrayList<Order> getBidList() {
        return bidList;
    }

    public void setBidList(ArrayList<Order> bidList) {
        this.bidList = bidList;
    }

    public ArrayList<String> getFreelancerInfo() {
        return freelancerInfo;
    }

    public void setFreelancerInfo(ArrayList<String> freelancerInfo) {
        this.freelancerInfo = freelancerInfo;
    }

    public ArrayList<String> getNotifications() {
        return notifications;
    }

    public String getFreelancerUsername() {
        return freelancerUsername;
    }

    public void setFreelancerUsername(String freelancerUsername) {
        this.freelancerUsername = freelancerUsername;
    }
}
