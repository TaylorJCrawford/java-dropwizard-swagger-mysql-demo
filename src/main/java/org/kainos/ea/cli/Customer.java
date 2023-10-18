package org.kainos.ea.cli;

public class Customer {

    private int CustomerID;

    public Customer(int customerID) {
        CustomerID = customerID;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }
}
