package org.kainos.ea.resources;

import io.swagger.annotations.Api;
import org.kainos.ea.api.OrderService;
import org.kainos.ea.cli.Order;
import org.kainos.ea.cli.OrderRequest;
import org.kainos.ea.cli.ProductRequest;
import org.kainos.ea.client.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.sql.SQLException;
import java.util.List;

@Api("Engineering Academy Dropwizard Order API")
@Path("/api")
public class OrderController {

    private OrderService orderService = new OrderService();

    @GET
    @Path("/orders")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getOrders() {
        try{
            return Response.ok(orderService.getAllOrders()).build();
        } catch (FailedToGetOrdersException e) {
            return Response.serverError().build();

        }
    }


    @POST
    @Path("/orders")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createOrder (OrderRequest order) {

        try {
            return Response.ok(orderService.createOrder(order)).build();
        } catch (SQLException e) {
            System.err.println(e.getMessage());
            return Response.serverError().build();
        } catch (isOrderDateOlderThan1Year | CustomerIDNotValidException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        }



//        try {
//            return Response.ok(productService.createProduct(product)).build();
//        } catch (FailedToCreateProductException e) {
//            System.err.println(e.getMessage());
//
//            return Response.serverError().build();
//        } catch (InvalidProductException e) {
//            System.err.println(e.getMessage());
//            return Response.status(Response.Status.BAD_REQUEST).entity(e.getMessage()).build();
//        }


    }

    @PUT
    @Path("/orders/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateOrder(@PathParam("id") int id, OrderRequest order) {
        try {
            orderService.updateOrder(id, order);
            return Response.ok().build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (isOrderDateOlderThan1Year | CustomerIDNotValidException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @DELETE
    @Path("/orders/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteOrder(@PathParam("id") int id) {

        try {
            orderService.deleteOrder(id);
            return Response.ok().build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (OrderDoesNotExistException e) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
    }

    @GET
    @Path("/orders/total")
    @Produces(MediaType.APPLICATION_JSON)
    public Response totalOrders() {
        try {
            return Response.ok(orderService.TotalOrders()).build();
        } catch (FailedToGetOrdersException e) {
            //throw new RuntimeException(e);
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/orders/most")
    @Produces(MediaType.APPLICATION_JSON)
    public Response customerWithMostOrders() {
        try {
            //return orderService.CustomerWithMostOrders();
            return Response.ok(orderService.customerWithTheLowestOrders()).build();
        } catch (FailedToGetOrdersException e) {
            //throw new RuntimeException(e);
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/orders/lowest")
    @Produces(MediaType.APPLICATION_JSON)
    public Response customerWithLowestOrders() {
        try {
            return Response.ok(orderService.customerWithTheLowestOrders()).build();
        } catch (FailedToGetOrdersException e) {
            //throw new RuntimeException(e);
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/orders/lastweek")
    @Produces(MediaType.APPLICATION_JSON)
    public Response ordersLastWeek() {
        try {
            return Response.ok(orderService.ordersLastWeek()).build();
        } catch (FailedToGetOrdersException e) {
            //throw new RuntimeException(e);
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/orders/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response orderById(@PathParam("id") int id) {

        try {
            return Response.ok(orderService.getOrderByID(id)).build();
        } catch (OrderDoesNotExistException e) {
            System.err.println(e.getMessage());
            return Response.status(Response.Status.BAD_REQUEST).build();
        } catch (FailedToGetOrdersException e) {
            System.err.println(e.getMessage());
            return Response.serverError().build();
        }

    }
}
