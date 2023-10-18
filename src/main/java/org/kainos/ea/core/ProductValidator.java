package org.kainos.ea.core;

import org.kainos.ea.cli.ProductRequest;

public class ProductValidator {

    public String isProductValid (ProductRequest product) {

        if (product.getName().length() > 50) {
            return "Name is greater than 50 Chars.";
        }

        if (product.getDescription().length() > 500) {
            return "Description greater than 500 Chars.";
        }

        if (product.getPrice() < 10) {
            return "Price is less than £10";
        }

        return null;
    }
}
