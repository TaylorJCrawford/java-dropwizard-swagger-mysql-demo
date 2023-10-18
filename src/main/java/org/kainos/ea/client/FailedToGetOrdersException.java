package org.kainos.ea.client;

public class FailedToGetOrdersException extends Exception {

    @Override
    public String getMessage() {
        return "Failed To Get Orders From Database";
    }
}
