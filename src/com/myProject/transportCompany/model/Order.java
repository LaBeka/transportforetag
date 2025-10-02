package com.myProject.transportCompany.model;


import com.myProject.transportCompany.builderObject.Vehicle;
import com.myProject.transportCompany.discountstrategy.RouteStrategy;
import com.myProject.transportCompany.exceptions.VehicleNotAvailableException;
import com.myProject.transportCompany.generatemodels.VehicleManagerBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        Optional<Vehicle> vehicle = Optional.empty();
        double result = 0.0;
        if(deliverySpeed[0].equalsIgnoreCase("fast")){
            //if it customer initially wants to order a fast delivery,  Van gets assigned to it because vans drive single routes
            vehicle = VehicleManagerBuilder.getInstance().assignVanToOrder(this);
            if(vehicle.isEmpty()){
                throw new VehicleNotAvailableException("Exception: No available van to assign for fast delivery.");
            }
            double multiplier =  1.55;
            result = baseCost * multiplier;
        } else {
            vehicle = VehicleManagerBuilder.getInstance().assignTruckToOrder(this);

            if(vehicle.isEmpty()){
                throw new VehicleNotAvailableException("Exception: No available truck to assign for slow delivery.");
            }
            double multiplier =  1.05;
            result = baseCost * multiplier;
        }

        vehicle.ifPresent(v -> {
            this.vehicle = v;
            v.setCapacity(-customer.getWeight());
            v.setAvailable(false);
        });
        return result;
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

        public Builder id() {
            String idd = (customer.getName().subSequence(0, 3)).toString() +
                    customer.getName().subSequence(customer.getName().length()-3, customer.getName().length());
            this.id = (idd.replaceAll(" ", "").toUpperCase() + LocalDateTime.now().format(DateTimeFormatter.ofPattern("02-10-25")) + LocalDateTime.now().getSecond());
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
        public Optional<Order> build() {

            try{
                return Optional.of(new Order(this));
            } catch(VehicleNotAvailableException e){
                System.out.println(e.getMessage());
                return Optional.empty();
            }
        }
    }

    public void chooseStrategy(RouteStrategy strategy ){
        strategy.chooseRoute(this);
    }

    @Override
    public String toString() {
        String str = String.format("Order id: %s customer: %s %s %s price: %.2f %s",
                id,
                customer.getName(),
                route.toString(),
                completed?"is completed" : "is not completed",
                price,
                vehicle.toString()) ;

        return str;
    }
}
