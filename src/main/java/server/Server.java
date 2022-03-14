package server;

import orderfactory.EntityOrderFactory;
import orderfactory.OrderFactory;

import java.util.ArrayList;
import java.util.stream.Stream;

public class Server {
    private Burse burse = Burse.getInstance();
    private ArrayList<Customer> customers;
    private ArrayList<Freelancer> freelancers;
    private Stream source;

    public void start() {

    }

    public void stop() {

    }

    public static void main(String[] args) {
        OrderFactory orderFactory = new EntityOrderFactory();
        Customer customer = new Customer(orderFactory);
        System.out.println(customer.toString()); //хотя бы это работает и славно

    }

}
