package org.kainos.ea.client;

public class FailedToCreateProductException extends Throwable {

    @Override
    public String getMessage() {
        return "Failed To Create A New Product!";
    }
}
