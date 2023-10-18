package org.kainos.ea.db;

import org.kainos.ea.cli.Order;
import org.kainos.ea.cli.OrderRequest;
import org.kainos.ea.cli.Product;
import org.kainos.ea.cli.ProductRequest;
import org.kainos.ea.client.CustomerIDNotValidException;
import org.kainos.ea.client.OrderDoesNotExistException;
import org.kainos.ea.client.isOrderDateOlderThan1Year;
import org.kainos.ea.core.OrderValidator;

import java.io.FileInputStream;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.sql.Date;
import java.util.Properties;

public class OrderDao {
    private DatabaseConnector databaseConnector = new DatabaseConnector();
    public List<Order> getAllOrders() throws SQLException {

        Connection c = databaseConnector.getConnection();

            Statement st = c.createStatement();

            ResultSet rs = st.executeQuery("SELECT OrderID, CustomerID, orderDate FROM `Order`;");

            List<Order> orderList = new ArrayList<>();

            while (rs.next()) {
                Order order = new Order(
                        rs.getInt("CustomerID"),
                        rs.getInt("OrderID"),
                        rs.getDate("OrderDate"));

                orderList.add(order);
            }

            return orderList;
    }

    public Order getOrderById (int id) throws SQLException {

        Connection c = databaseConnector.getConnection();

        Statement st = c.createStatement();

        ResultSet rs = st.executeQuery("SELECT OrderID, CustomerID, orderDate FROM `Order` WHERE OrderID=" + id);

        while (rs.next()) {
            Order order = new Order(
                    rs.getInt("OrderID"),
                    rs.getInt("CustomerID"),
                    rs.getDate("orderDate"));
            return order;

        }
        return null;
    }

    private OrderValidator orderValidator = new OrderValidator();

    public int createOrder (OrderRequest order) throws SQLException, CustomerIDNotValidException,
            isOrderDateOlderThan1Year {

        Connection c = databaseConnector.getConnection();

        if (!orderValidator.isCustomerIdValid(order.getCustomerId())) {
            throw new CustomerIDNotValidException();
        }

        // Check Date Is Valid: isOrderDateOlderThan1Year
        if (!orderValidator.isOrderDateOlderThan1Year(order.getOrderDate())) {
            throw new isOrderDateOlderThan1Year();
        }


        String insertStatement = "INSERT INTO `Order` (OrderDate, CustomerID) VALUES (?, ?)";

        PreparedStatement st = c.prepareStatement(insertStatement, Statement.RETURN_GENERATED_KEYS);

        st.setDate(1, order.getOrderDate());
        st.setInt(2, order.getCustomerId());

        st.executeUpdate();

        ResultSet rs = st.getGeneratedKeys();

        if (rs.next()) {
            return rs.getInt(1);
        }

        return -1;
    }

    public void updateOrder(int id, OrderRequest order) throws SQLException, CustomerIDNotValidException,
            isOrderDateOlderThan1Year {
        Connection c = databaseConnector.getConnection();

        if (!orderValidator.isCustomerIdValid(id)) {
            throw new CustomerIDNotValidException();
        }

        if (orderValidator.isOrderDateOlderThan1Year(order.getOrderDate())) {
            throw  new isOrderDateOlderThan1Year();
        }

        String updateStatement = "UPDATE `Order` SET OrderDate = ?, CustomerID = ? WHERE OrderID = ?";

        PreparedStatement st = c.prepareStatement(updateStatement);

        st.setDate(1, order.getOrderDate());
        st.setInt(2, order.getCustomerId());
        st.setInt(3, id);

        st.executeUpdate();
    }

    public void deleteOrder(int id) throws SQLException, OrderDoesNotExistException {
        Connection c = databaseConnector.getConnection();

        if (!orderValidator.isOrderIdValid(id)) {
            throw new OrderDoesNotExistException();
        }

        String deleteStatement = "DELETE FROM Order WHERE OrderID = ?";

        PreparedStatement st = c.prepareStatement(deleteStatement);

        st.setInt(1, id);

        st.executeUpdate();
    }
}

