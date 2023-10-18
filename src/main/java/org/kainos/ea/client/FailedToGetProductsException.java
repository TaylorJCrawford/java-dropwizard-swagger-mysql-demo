package org.kainos.ea.client;

public class FailedToGetProductsException extends Throwable {

    @Override
    public String getMessage() {
        return "Failed To Get Products From Database";
    }
}
