package com.myProject.transportCompany.model.vehicle;

public class Van extends Vehicle {

    public Van(String regNumber, double capacity) {
        super(regNumber, capacity);
    }

    @Override
    public boolean isAvailable() {
        return isAvailable;
    }

    @Override
    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    @Override
    public String getType() {
        return "Van";
    }

    @Override
    public boolean hasDriver() {
        return driver != null;
    }
}
