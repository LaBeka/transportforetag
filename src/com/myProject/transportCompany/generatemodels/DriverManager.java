package com.myProject.transportCompany.generatemodels;

import com.myProject.transportCompany.interfaces.IDriverCreator;
import com.myProject.transportCompany.model.Driver;
import com.myProject.transportCompany.model.LicenceType;

import java.util.*;

public class DriverManager implements IDriverCreator {

    Map<String, Driver> drivers = new HashMap<>();

    static DriverManager driverManager;

    @Override
    public Map<String, Driver> getDrivers(){
        return Collections.unmodifiableMap(this.drivers);
    }

    @Override
    public void createDriver(String name, LicenceType type, String schedule) {
        Driver driver = new Driver(name, type, schedule);
        this.drivers.put(driver.getName(), driver);
    }

    @Override
    public List<Driver> getDriverList() {
        return new ArrayList<>(getDrivers().values());
    }

    @Override
    public void initializeDrivers() {
        createDriver("Anna Smith", LicenceType.B, "schedule1");
        createDriver("Joe Doe", LicenceType.A, "schedule2");
        createDriver("Hanna Doe",LicenceType.C, "schedule3");
        createDriver("Joe Smith",LicenceType.B, "schedule4");
        createDriver("Ray Sm",LicenceType.A, "schedule5");
        createDriver("Sam Kal",LicenceType.C, "schedule6");
        createDriver("Denis Al",LicenceType.B, "schedule7");
        createDriver("Timur A",LicenceType.A, "schedule8");
        createDriver("Talant T",LicenceType.C, "schedule9");
        createDriver("D Almaz",LicenceType.B, "schedule10");
    }

    @Override
    public void printDriverList() {
        for (Driver d: drivers.values()) {
            System.out.println(d.toString());
        }
        System.out.println();
    }

    @Override
    public void printAvailableDriverList() {
        for (Driver d: drivers.values()) {
            if(d.isAssignedToVehicle()) {
                System.out.println(d.toString());
            }
        }
        System.out.println();
    }

    public static DriverManager getInstance() {
        if (driverManager == null) {
            driverManager = new DriverManager();
        }
        return driverManager;
    }
}
