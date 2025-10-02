package com.myProject.transportCompany.interfaces;

import com.myProject.transportCompany.model.Driver;
import com.myProject.transportCompany.model.LicenceType;

import java.util.List;
import java.util.Map;

public interface IDriverCreator {
    Map<String, Driver> getDrivers();
    void createDriver(String name, LicenceType type, String schedule);
    List<Driver> getDriverList();
    void initializeDrivers();
    void printDriverList();
    void printAvailableDriverList();

}
