package org.kainos.ea.cli;

public class Product implements Comparable<Product>{

    private int ProductId;
    private String Name;
    private String Description;
    private double Price;

    public Product(int productId, String name, String description, double price) {
        ProductId = productId;
        Name = name;
        Description = description;
        Price = price;
    }

    public int getProductId() {
        return ProductId;
    }

    public void setProductId(int productId) {
        ProductId = productId;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public double getPrice() {
        return Price;
    }

    public void setPrice(double price) {
        Price = price;
    }

    @Override
    public int compareTo(Product product) {
        // return this.getName().compareTo(product.getName()); // Compare By Name
        return Double.compare(this.getPrice(), product.getPrice());
    }

    @Override
    public String toString() {
        return "Product name: " + this.getName() + ", Product price: £ " + this.getPrice();
    }
}
