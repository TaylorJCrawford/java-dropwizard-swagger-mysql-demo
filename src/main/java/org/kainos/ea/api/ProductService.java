package org.kainos.ea.api;

import org.kainos.ea.cli.Product;
import org.kainos.ea.cli.ProductRequest;
import org.kainos.ea.client.*;
import org.kainos.ea.core.ProductValidator;
import org.kainos.ea.db.ProductDao;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class ProductService {

    private ProductDao productDao = new ProductDao();
    private ProductValidator productValidator = new ProductValidator();
    public List<Product> getAllProducts() throws FailedToGetProductsException {

        List<Product> productList = null;
        try {
            productList = productDao.getAllProducts();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            throw new FailedToGetProductsException();
            //throw new RuntimeException(e);
        }

        try {
            productList.get(1000000);
        } catch (IndexOutOfBoundsException e) {
            System.out.println("-------------------------------");
            System.out.println(e.getMessage());
        }


        // totalPriceOfProduct = productList.stream().mapToDouble(product -> product.getPrice()).sum();

        double totalPriceOfProduct = 0;
        double totalPriceOfExpensiveProduct = 0;
        // Update your `ProductService` class to use a for each loop to print out the total price of all of the products in your database that cost less than £100
        for (Product product : productList) {
            if (product.getPrice() < 100) {
                totalPriceOfProduct += product.getPrice();
            } else {
                totalPriceOfExpensiveProduct += product.getPrice();
            }
        }

//        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5);
//        intList.stream().forEach(System.out::println);

        List<Integer> intList = Arrays.asList(1, 2, 2, 3, 4, 5);
        Set<Integer> intSet = new HashSet<>(intList);

        // intSet.stream().forEach(System.out::println);

        //Collection.sort(productList);


        //System.out.println("Total price of all products: £" + totalPriceOfProduct);
        // System.out.println("Total price of expensive products: £" + totalPriceOfExpensiveProduct);

       // Product myProductTest = new Product(1, "Heater", "Heater Is Hot!", 49.99);
       // System.out.println(myProductTest);

        //Collections.sort(productList);

        //System.out.println(Collections.max(productList));

        //productList.stream().filter(product -> product.getPrice() > 10).forEach(System.out::println);
        //List<Product> cheapProducts = productList.stream().filter(product -> product.getPrice() < 10).collect(Collectors.toList());

        // cheapProducts.forEach(System.out::println);

        return productList;
    }

    public Product getProductById (int id) throws FailedToGetProductsException, ProductDoesNotExistException {

        try {
            Product product = productDao.getProductById(id);

            if (product == null) {
                throw new FailedToGetProductsException();
            }

            return product;
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            throw new FailedToGetProductsException();
        }
    }

    public int createProduct (ProductRequest product) throws FailedToCreateProductException, InvalidProductException {
        // Create a method named `createProduct` in your `ProductService`
        // that takes a product object as a parameter and returns the id from the dao.
        // Throw a new `FailedToCreateProductException` exception if an invalid
        // id is returned or an exception is thrown from the dao

        try {
            String productValid = productValidator.isProductValid(product);

            if (productValid != null) {
                throw new InvalidProductException(productValid);
            }

            int newProductId = productDao.createProduct(product);

            if (newProductId == -1) {
                throw new FailedToCreateProductException();
            }

            return newProductId;
        } catch (SQLException e) {
            System.err.println(e.getMessage());

            throw new FailedToCreateProductException();

        }
    }

    public void updateProduct(int id, ProductRequest product) throws InvalidProductException, ProductDoesNotExistException,
            FailedToUpdateProductException {

        //Create a method named `updateProduct` in your `ProductService` that takes the id of the product you want
        // to update and the product object as parameters. Throw a new `FailedToUpdateProductException`
        // exception if an invalid id is returned or an exception is thrown from the dao.
        // If the product does not exist in the database throw a new `ProductDoesNotExistException`

        try {
            String validation = productValidator.isProductValid(product);

            if (validation != null) {
                throw new InvalidProductException(validation);
            }

            Product productToUpdate = productDao.getProductById(id);

            if (productToUpdate == null) {
                throw new ProductDoesNotExistException();
            }

            productDao.updateProduct(id, product);
        } catch (SQLException e) {
            System.err.println(e.getMessage());

            throw new FailedToUpdateProductException();
        }
    }

    public void deleteProduct(int id) throws ProductDoesNotExistException, FailedToDeleteProductException {
        try {
            Product productToDelete = productDao.getProductById(id);

            if (productToDelete == null) {
                throw new ProductDoesNotExistException();
            }

            productDao.deleteProduct(id);
        } catch (SQLException e) {
            System.err.println(e.getMessage());

            throw new FailedToDeleteProductException();
        }
    }
}