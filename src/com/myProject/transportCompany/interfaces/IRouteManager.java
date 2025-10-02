package com.myProject.transportCompany.interfaces;

import com.myProject.transportCompany.model.Location;
import com.myProject.transportCompany.model.Order;

import java.util.List;

public interface IRouteManager {

    void init();
    void generateStraightDistances();
    void generateIntermediateCities(Location start, Location end);
    void negotiatingMenu(Order order);
    void expensiveOrder(Order order);
    void cheapOrder(Order order);
    double getStraightDistances(Location start, Location end);
    double calculateDistance(Location start, List<Location> list, Location end);
}
