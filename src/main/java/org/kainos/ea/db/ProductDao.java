package org.kainos.ea.db;

import org.kainos.ea.cli.Order;
import org.kainos.ea.cli.Product;
import org.kainos.ea.cli.ProductRequest;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDao {

    private DatabaseConnector databaseConnector = new DatabaseConnector();
    public List<Product> getAllProducts() throws SQLException {

        Connection c = databaseConnector.getConnection();

        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery("SELECT ProductID, Name, Description, Price FROM Product;");

        List<Product> productList = new ArrayList<>();

        while (rs.next()) {
            Product product = new Product(
                    rs.getInt("ProductID"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getDouble("Price"));

            productList.add(product);
        }

        return productList;
    }

    public Product getProductById(int productID) throws SQLException {
        // In your `ProductDao` create a new method called `getProductById`
        // that takes an ID as a parameter and returns the product with that ID


        Connection c = databaseConnector.getConnection();

        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery("SELECT ProductID, Name, Description, Price FROM Product " +
                "WHERE ProductID=" + productID);

        while (rs.next()) {
            Product product = new Product(
                    rs.getInt("ProductID"),
                    rs.getString("Name"),
                    rs.getString("Description"),
                    rs.getDouble("Price"));
            return product;

        }
        return null;
    }

    public int createProduct(ProductRequest product) throws SQLException {
        // Create a method named `createProduct` in your `ProductDao` that takes a
        // product object as a parameter and returns the id of the created product.
        // You should use prepared statements as it will protect you against SQL injection

        Connection c = databaseConnector.getConnection();

        String insertStatement = "INSERT INTO Product (Name, Description, Price) VALUES (?, ?, ?)";

        PreparedStatement st = c.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);

        st.setString(1, product.getName());
        st.setString(2, product.getDescription());
        st.setDouble(3, product.getPrice());

        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();

        if (rs.next()) {
            return rs.getInt(1);
        }

        return -1;

    }

    public void updateProduct(int id, ProductRequest product) throws SQLException {
        Connection c = databaseConnector.getConnection();

        String updateStatement = "UPDATE Product SET Name = ?, Description = ?, Price = ? WHERE ProductID = ?";

        PreparedStatement st = c.prepareStatement(updateStatement);

        st.setString(1, product.getName());
        st.setString(2, product.getDescription());
        st.setDouble(3, product.getPrice());
        st.setInt(4, id);

        st.executeUpdate();
    }

    public void deleteProduct(int id) throws SQLException {
        Connection c = databaseConnector.getConnection();

        String deleteStatement = "DELETE FROM Product WHERE ProductID = ?";

        PreparedStatement st = c.prepareStatement(deleteStatement);

        st.setInt(1, id);

        st.executeUpdate();
    }
}
