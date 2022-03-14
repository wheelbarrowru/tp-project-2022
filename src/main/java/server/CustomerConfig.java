package server;

import orderfactory.EntityOrderFactory;
import orderfactory.IndividOrderFactory;
import orderfactory.OrderFactory;

import java.util.*;

public class CustomerConfig {
    public static final String[] entityFields = {"companyName", "dateOfFoundation", "registrationNumber", "contacts"};
    public static final String[] individFields = {"name", "experience", "contacts"};
    private OrderFactory orderFactory;

    CustomerConfig(Map<String, String> customerInfoMap) {
        if ("Entity".equals(customerInfoMap.get("type"))) {
            orderFactory = new EntityOrderFactory();
            Customer customer = new Customer(orderFactory);
            var fieldsArray = new String[4];
            for (Map.Entry<String, String> field : customerInfoMap.entrySet()) {
                switch (field.getKey()) {
                    case "companyName" -> fieldsArray[0] = field.getValue();
                    case "dateOfFoundation" -> fieldsArray[1] = field.getValue();
                    case "registrationNumber" -> fieldsArray[2] = field.getValue();
                    case "contacts" -> fieldsArray[3] = field.getValue();
                    case "username" -> customer.setCustomerUsername(field.getValue());
                }
            }

            customer.setCustomerInfo(new ArrayList<>(Arrays.asList(fieldsArray)));
        } else {
            if ("Individ".equals(customerInfoMap.get("type"))) {
                orderFactory = new IndividOrderFactory();
                Customer customer = new Customer(orderFactory);
                var fieldsArray = new String[3];
                for (Map.Entry<String, String> field : customerInfoMap.entrySet()) {
                    switch (field.getKey()) {
                        case "name" -> fieldsArray[0] = field.getValue();
                        case "experience" -> fieldsArray[1] = field.getValue();
                        case "contacts" -> fieldsArray[2] = field.getValue();
                        case "viewPermission" -> customer.setViewPermission("yes".equals(field.getValue()));
                        case "username" -> customer.setCustomerUsername(field.getValue());
                    }
                }
                customer.setCustomerInfo(new ArrayList<>(Arrays.asList(fieldsArray)));
            } else {
                throw new RuntimeException();

            }
        }

    }
}
