package org.kainos.ea.cli;

import java.util.Date;

public class Order implements Comparable<Order>{

    private int customerId;
    private int orderId;
    private Date orderDate;

    public Order(int customerId, int orderId, Date orderDate) {
        this.setCustomerId(customerId);
        this.setOrderId(orderId);
        this.setOrderDate(orderDate);
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public Date getOrderDate() {
        return orderDate;
    }

    public void setOrderDate(Date orderDate) {
        this.orderDate = orderDate;
    }

    @Override
    public int compareTo(Order order) {
        return order.getOrderDate().compareTo(this.orderDate);
    }

    @Override
    public String toString() {
        return "OrderID: " + this.orderId + " CustomerID: " + this.customerId + " Order Date: " + this.orderDate;
    }
}
