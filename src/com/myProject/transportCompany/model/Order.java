package com.myProject.transportCompany.model;


import com.myProject.transportCompany.builderObject.Vehicle;
import com.myProject.transportCompany.discountstrategy.RouteStrategy;
import com.myProject.transportCompany.generatemodels.VehicleManagerBuilder;

import java.time.LocalDate;
import java.util.Optional;

public class Order {

    private String id;
    private Customer customer;
    private Route route;
    private boolean completed;
    private double price;
    private Vehicle vehicle;

    public Order(Customer customer) {
        this.id = customer.getName() + LocalDate.now().toString();
        this.customer = customer;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Route getRoute() {
        return route;
    }

    public void setRoute(Route route) {
        this.route = route;
    }

    public Vehicle getVehicle() {
        return vehicle;
    }

    public boolean checkIfVehicle() {
        return vehicle != null;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    private double calculateTotal() {
        double baseRate = 0.05;
        String[] deliverySpeed = this.customer.getPackageInfo().split(" ");
        double weight = this.customer.getWeight();
        double distance = this.route.getDistance();
        double baseCost = weight * distance * baseRate;
        if(deliverySpeed[0].equalsIgnoreCase("fast")){
            Optional<Vehicle> assignedVan = VehicleManagerBuilder.getInstance().assignVanToOrder(this);
            assignedVan.ifPresent(value -> this.vehicle = value);

            double multiplier =  1.55;
            return baseCost * multiplier;
        } else {
            Optional<Vehicle> assignedTruck = VehicleManagerBuilder.getInstance().assignTruckToOrder(this);
            assignedTruck.ifPresent(value -> this.vehicle = value);

            double multiplier =  1.05;
            return baseCost * multiplier;
        }
    }

    private Order(Builder builder) {
        this.id =  builder.id;
        this.customer =  builder.customer;
        this.route =   builder.route;
        this.completed = false;
        this.price =  calculateTotal();
    }

    public static class Builder {
        private String id;
        private Customer customer;
        private Route route;

        public Builder id(String id) {
            this.id = id;
            return this;
        }
        public Builder customer(Customer customer) {
            this.customer = customer;
            return this;
        }
        public Builder route(Route route) {
            this.route = route;
            return this;
        }
        public Order build() {
            return new Order(this);
        }
    }

    public void chooseStrategy(RouteStrategy strategy ){
        strategy.chooseRoute(this);
    }

    @Override
    public String toString() {
        return STR."Order {customer=\{
                customer.getName()
                }, route=\{
                route.toString()
                }, is completed=\{
                completed
                }, price=\{price}, vehicle=\{vehicle.getType()}}";
    }
}
