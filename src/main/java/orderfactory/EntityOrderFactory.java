package orderfactory;

import server.Customer;

import java.util.ArrayList;

public class EntityOrderFactory implements OrderFactory {
    @Override
    public Order createOrder(ArrayList<String> orderInfo, Customer customer) {
        Order order = new EntityOrder();
        order.setOrderInfo(orderInfo);
        order.setOrderCustomer(customer);
        return order;
    }
}
