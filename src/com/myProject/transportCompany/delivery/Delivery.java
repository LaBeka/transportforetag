package com.myProject.transportCompany.delivery;

import com.myProject.transportCompany.model.Customer;
import com.myProject.transportCompany.model.Order;
import com.myProject.transportCompany.model.Route;

public class Delivery {
    private Customer customer;
    private String load;
    private Route route;
    private Status status;
    private Order order;

    public Delivery(Order order, Customer customer, Route route, String load) {
        this.order = order;
        this.customer = customer;
        this.route = route;
        this.status = Status.START;
    }

    public Customer getDriver() {
        return customer;
    }

    public void setDriver(Customer customer) {
        this.customer = customer;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}
