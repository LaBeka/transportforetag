package com.myProject.transportCompany.interfaces;

import com.myProject.transportCompany.model.Driver;
import com.myProject.transportCompany.model.Order;

public interface Assignable {
    void assignDriver(Driver driver);
    void assignVehicleToOrder(Order order);
}
