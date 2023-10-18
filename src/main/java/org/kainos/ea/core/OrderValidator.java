package org.kainos.ea.core;

import org.kainos.ea.cli.Customer;
import org.kainos.ea.cli.Order;
import org.kainos.ea.db.CustomerDao;
import org.kainos.ea.db.OrderDao;

import java.sql.Date;
import java.sql.SQLException;
import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class OrderValidator {

    private CustomerDao customerDao = new CustomerDao();
    private OrderDao orderDao = new OrderDao();

    public boolean isCustomerIdValid(int customerID) throws SQLException {

        List<Customer> customerList = new ArrayList<>();
        customerList = customerDao.getAllCustomers();

        customerList.stream()
                .filter(customer -> customer.getCustomerID() == customerID)
                .collect(Collectors.toList());

        if (customerList.size() > 0) {
            return true;
        }

        return false;
    }

    public boolean isOrderDateOlderThan1Year (Date orderDate) {

        Date currentDate = (Date) Date.from(Instant.now().minus(Duration.ofDays(365)));
        // (order.getOrderDate().isBefore(LocalDateTime.now().minusYears(1)))
        return orderDate.after(currentDate);

    }

    public boolean isOrderIdValid(int orderID) throws SQLException {

        List<Order> orderList = new ArrayList<>();
        orderList = orderDao.getAllOrders();

        orderList.stream()
                .filter(order -> order.getOrderId() == orderID)
                .collect(Collectors.toList());

        if (orderList.size() > 0) {
            return true;
        }

        return false;
    }
}
