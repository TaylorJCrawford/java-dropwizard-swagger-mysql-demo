package org.kainos.ea.api;

import org.apache.commons.lang3.time.DateUtils;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.kainos.ea.cli.Order;
import org.kainos.ea.cli.OrderRequest;
import org.kainos.ea.cli.Product;
import org.kainos.ea.cli.ProductRequest;
import org.kainos.ea.client.*;
import org.kainos.ea.db.OrderDao;

import java.sql.SQLException;
import java.sql.SQLOutput;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class OrderService {

    private OrderDao orderDao = new OrderDao();

    public List<Order> getAllOrders() throws FailedToGetOrdersException {

        List<Order> orderList = null;
        try {
            orderList = orderDao.getAllOrders();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return orderList; // orderList
    }

    public List<Order> ordersLastWeek() throws FailedToGetOrdersException {
        // Update your `OrderService` to only show orders from the last week
        List<Order> orderList = getAllOrders();

        Date date = new Date(); // Or where ever you get it from
        Date daysAgo = new DateTime(date).minusDays(7).toDate();

        List<Order> ordersLastWeek = orderList.stream().filter(order ->
                order.getOrderDate().after(daysAgo) &&  order.getOrderDate().before(date)).collect(Collectors.toList());
        ordersLastWeek.forEach(System.out::println);

        return ordersLastWeek;
    }

    public List<Order> OrderDateDescending() throws FailedToGetOrdersException {
        // Update your `OrderService` and `Order` classes to print out order the list by order date descending
        List<Order> orderList = getAllOrders();

        Collections.sort(orderList);

        return orderList;
    }

    public List<Order> OrdersByCustomerID(int customerId) throws FailedToGetOrdersException {
        // Update your `OrderService` to only show orders from customer with `CustomerID` 1

        List<Order> orderList = getAllOrders();

        List<Order> OrdersForCustomerId = orderList.stream().filter(order ->
                order.getCustomerId() == customerId).collect(Collectors.toList());

        return OrdersForCustomerId;
    }

    public List<Order> MostRecentOrder() throws FailedToGetOrdersException {
        // Update your `OrderService` to only show the most recent order

        List<Order> orderList = getAllOrders();

        //        Order shortest = orderList.stream()
        //                .max(Comparator.comparing(order -> order.getOrderDate()))
        //                .get();

        Collections.sort(orderList);
        List<Order> mostRecentOrder = new ArrayList<>();
        mostRecentOrder.add(orderList.get(0));

        // Collections.max(orderList)

        return MostRecentOrder();
    }

    public List<Order> OldestOrder()  throws FailedToGetOrdersException{
        // Update your `OrderService` to only show the oldest order

        List<Order> orderList = getAllOrders();

        Collections.sort(orderList, Collections.reverseOrder());
        List<Order> oldestOrder = new ArrayList<>();
        oldestOrder.add(orderList.get(0));

        return oldestOrder;
    }

    public String TotalOrders() throws FailedToGetOrdersException{
        // Update your `OrderService` to show the total count of all orders

        List<Order> orderList = getAllOrders();
        return "The Total Number Of Orders: " + orderList.size();
    }

    private HashMap<Integer, Integer> hashMapOfCustomers () throws FailedToGetOrdersException{
        HashMap<Integer, Integer> customerCounter = new HashMap<Integer, Integer>();

        List<Order> orderList = getAllOrders();

        for (Order order : orderList) {
            int currentCustomerId = order.getCustomerId();
            if (customerCounter.get(currentCustomerId) != null) {
                // Customer ID exists in hashmap. -> Need to update value by + 1.
                customerCounter.put(currentCustomerId, customerCounter.get(currentCustomerId) + 1);
            } else {
                customerCounter.put(currentCustomerId, 1);
            }
        }

        // Map<Integer, Long> orderMap = orderList
        //        .stream()
        //        .collect(Collectors.groupingBy(Order::getCustomerId, Collectors.counting()));

        return customerCounter;
    }

    public String CustomerWithMostOrders() throws FailedToGetOrdersException{
        // Update your `OrderService` to show the customer ID with the most orders

        HashMap<Integer, Integer> customerCounter = hashMapOfCustomers();

        // Loop Through Hashmap -> trying to find the most orders for customer.
        int largestOrderIndex = -1;
        int orderValue = -1;
        for (int i: customerCounter.keySet()) {
            if (customerCounter.get(i) > orderValue) {
                orderValue = customerCounter.get(i);
                largestOrderIndex = i;
            }
        }

        // int largestOrderCount = customerCounter.get(largestOrderIndex);

        return "Customer ID: " + largestOrderIndex + " with " + orderValue + " order(s). Has the largest number of orders.";
    }

    public String customerWithTheLowestOrders() throws FailedToGetOrdersException {
        // Update your `OrderService` to show the customer ID with the least orders
        HashMap<Integer, Integer> customerCounter = hashMapOfCustomers();

        // System.out.println(customerCounter);

        // Loop Through Hashmap -> trying to find the most orders for customer.
        int lowestOrderIndex = 100000;
        int orderValue = 100000;
        for (int i: customerCounter.keySet()) {
            if (customerCounter.get(i) < orderValue) {
                orderValue = customerCounter.get(i);
                lowestOrderIndex = i;
            }
        }

        // int lowestOrderCount = customerCounter.get(lowestOrderIndex);

        return "Customer ID: " + lowestOrderIndex + " with " + orderValue + " order(s). Has the lowest number of orders.";
    }

    public Order getOrderByID (int id) throws FailedToGetOrdersException, OrderDoesNotExistException {

        try {

            Order order = orderDao.getOrderById(id);

            if (order == null) {
                throw new OrderDoesNotExistException();
            }

            return order;

        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new OrderDoesNotExistException();
        }
    }

    public int createOrder (OrderRequest order) throws SQLException, CustomerIDNotValidException,
            isOrderDateOlderThan1Year {

        int newOrderId = orderDao.createOrder(order);


        return newOrderId;
    }

    public void updateOrder(int id, OrderRequest product) throws SQLException, CustomerIDNotValidException,
            isOrderDateOlderThan1Year {

            orderDao.updateOrder(id, product);
    }

    public void deleteOrder(int id) throws SQLException, OrderDoesNotExistException {
        orderDao.deleteOrder(id);
    }
}