package org.kainos.ea.client;

public class CustomerIDNotValidException extends Exception {

    @Override
    public String getMessage() {
        return "Customer ID Is Not Valid!";
    }
}
