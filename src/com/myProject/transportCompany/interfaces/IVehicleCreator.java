package com.myProject.transportCompany.interfaces;

import com.myProject.transportCompany.model.Driver;
import com.myProject.transportCompany.model.Order;
import com.myProject.transportCompany.builderObject.Vehicle;

import java.util.List;
import java.util.Optional;

public interface IVehicleCreator {
    Optional<Vehicle> assignVanToOrder(Order order);
    Optional<Vehicle> assignTruckToOrder(Order order);
    List<Vehicle> getVehicles();
    void printNoDriverVehicles();
    void printAvailableVehicles();
    void print();
    void createVehicle(Vehicle vehicle, Optional<Driver> driver);
    void initializeVehicles(List<Driver> drivers);
    void generateVehicles();
    Vehicle getOneVehicle(String name);
    void updateVehicle(Order order);
}
