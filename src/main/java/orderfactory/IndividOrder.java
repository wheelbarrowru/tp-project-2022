package orderfactory;

import server.Freelancer;

import java.util.ArrayList;

public class IndividOrder extends Order {
    private boolean customerViewPermission;
    private ArrayList<Freelancer> waitingToViewPermissionList;

    @Override
    public ArrayList<Order> getCustomerHistoryInfo() {
        return customerViewPermission ? null : orderCustomer.getOrderList();
    }

    public void getCustomerViewPermission(Freelancer freelancer) {
        waitingToViewPermissionList.add(freelancer);
        orderCustomer.updateNotifications(String.format("User %s requests to view your order history",
                freelancer.getFreelancerUsername()));
    }

    public void notifyResultOfViewRequest() {
        setCustomerViewPermission(true);
        waitingToViewPermissionList.forEach(s -> s.updateNotifications(String.format(
                "Viewing %s history is allowed", orderCustomer.getCustomerUsername())));
    }

    public void setCustomerViewPermission(boolean customerViewPermission) {
        this.customerViewPermission = customerViewPermission;
    }
}
