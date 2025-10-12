package com.myProject.transportCompany.discountstrategy;

import com.myProject.transportCompany.generatemodels.AutoOrderManager;
import com.myProject.transportCompany.model.Order;
import com.myProject.transportCompany.model.Route;

import java.util.Comparator;
import java.util.List;

public class ShortestRouteStrategy implements RouteStrategy{

    @Override
    public void chooseRoute(Order order) {
        Route route = order.getRoute();
//        double newValue = (order.getPrice() * 10.0);
        double newValue = 99999.09;
        order.setPrice(newValue);
//        AutoOrderManager.getInstance().updateCurrentOrder(order);
        System.out.println(route.toString() + " for the price " + order.getPrice());
        System.out.println("Fast but Expensive Choose Route");
        AutoOrderManager.getInstance().completeOrder(order);
    }
}
