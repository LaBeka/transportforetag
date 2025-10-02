package com.myProject.transportCompany.generatemodels;

import com.myProject.transportCompany.builderObject.Truck;
import com.myProject.transportCompany.builderObject.Van;
import com.myProject.transportCompany.interfaces.IVehicleCreator;
import com.myProject.transportCompany.builderObject.Vehicle;
import com.myProject.transportCompany.model.Driver;
import com.myProject.transportCompany.model.Order;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class VehicleManagerBuilder implements IVehicleCreator {

    List<Vehicle> vehicles = new ArrayList<>();
    private static volatile VehicleManagerBuilder vehicleInstance;
    private List<Driver> drivers;

    private VehicleManagerBuilder() {}

    public static VehicleManagerBuilder getInstance() {
        if (vehicleInstance == null) {
            synchronized (VehicleManagerBuilder.class) {
                if (vehicleInstance == null) {
                    vehicleInstance = new VehicleManagerBuilder();
                }
            }
        }
        return vehicleInstance;
    }

    @Override
    public Optional<Vehicle> assignVanToOrder(Order order) {
        Vehicle van = null;
        for (Vehicle v : vehicles) {
            if(v.isAvailable() && v.getType().equalsIgnoreCase("van")) {
                v.assignVehicleToOrder(order);
                van = v;
                break;
            }
        }
        return Optional.of(van);
    }

    @Override
    public Optional<Vehicle> assignTruckToOrder(Order order) {
        Vehicle van = null;
        for (Vehicle v : vehicles) {
            if(v.isAvailable() && v.getType().equalsIgnoreCase("truck")) {
                v.assignVehicleToOrder(order);
                van = v;
                break;
            }
        }
        return Optional.of(van);
    }

    @Override
    public List<Vehicle> getVehicles() {
        return Collections.unmodifiableList(this.vehicles);
    }

    @Override
    public void printNoDriverVehicles() {
        for (Vehicle v : vehicles) {
            if(!v.hasDriver()) {
                System.out.println(v.toString());
            }
        }
        System.out.println();
    }

    @Override
    public void printAvailableVehicles() {
        for (Vehicle v : vehicles) {
            if(v.isAvailable()) {
                System.out.println(v.toString());
            }
        }
        System.out.println();
    }

    @Override
    public void print() {
        System.out.println("Vehicle: ");
        for (Vehicle v : vehicles) {
            System.out.println(v.toString());
        }
        System.out.println();
    }

    @Override
    public void createVehicle(Vehicle vehicle, Optional<Driver> driver) {
        this.vehicles.add(vehicle);
        if(driver.isPresent()){
            vehicle.assignDriver(driver.get());
            driver.get().setAssignedToVehicle(vehicle);
        }
    }

    @Override
    public void initializeVehicles(List<Driver> drivers) {
        this.drivers = drivers;
        generateVehicles();
    }

    @Override
    public void generateVehicles() {

        int regSuffix = 800;

        for (Driver driver : drivers) {
            if (driver.getLicenseClass().value.equalsIgnoreCase("B")) {
                createVehicle(
                        new Truck.Builder()
                                .regNumber("XYZ-" + regSuffix++)
                                .capacity(580.0)
                                .isAvailable(true)
                                .build(),
                        Optional.of(driver));
            } else if (driver.getLicenseClass().value.equalsIgnoreCase("C")) {
                createVehicle(
                        new Van.Builder()
                                .regNumber("XYZ-" + regSuffix++)
                                .capacity(1040.0)
                                .isAvailable(true)
                                .build(), Optional.of(driver));
            }
        }

        // Add some vehicles with no drivers (optional)
        createVehicle(new Van.Builder()
                .regNumber("XYZ-" + regSuffix++)
                .capacity(10.0).isAvailable(true)
                .build(),
                Optional.empty());
        createVehicle(new Truck.Builder()
                .regNumber("XYZ-" + regSuffix++)
                .capacity(900.0)
                .isAvailable(true)
                .build(),
                Optional.empty());
    }

    @Override
    public Vehicle getOneVehicle(String name) {
        for (Vehicle v : vehicles) {
            if (name.equalsIgnoreCase(v.getType())) {
                return v;
            }
        }
        return null;
    }

    @Override
    public void updateVehicle(Order order) {
        vehicles.stream()
                .filter(vehicle -> vehicle.equals(order.getVehicle()))
                .findFirst()
                .ifPresent(vehicle -> {vehicle.setAvailable(false);});
    }
}
