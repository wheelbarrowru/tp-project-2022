package orderfactory;

import server.Customer;

import java.util.ArrayList;

public class IndividOrderFactory implements OrderFactory {
    @Override
    public Order createOrder(ArrayList<String> orderInfo, Customer customer) {
        Order order = new IndividOrder();
        order.setOrderInfo(orderInfo);
        order.setOrderCustomer(customer);
        ((IndividOrder) order).setCustomerViewPermission(customer.getViewPermission());
        return order;
    }
}
