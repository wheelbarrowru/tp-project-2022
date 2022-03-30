package orderfactory;

import server.Customer;

import java.util.ArrayList;

public interface OrderFactory {
    public final static String[] orderFields = {"name", "occupation", "salary", "description"};

    Order createOrder(ArrayList<String> orderInfo, Customer customer);
}