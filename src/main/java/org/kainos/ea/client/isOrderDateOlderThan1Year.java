package org.kainos.ea.client;

public class isOrderDateOlderThan1Year extends Exception {


    @Override
    public String getMessage() {
        return "Order Date Is More Than A Year Ago!";
    }
}
