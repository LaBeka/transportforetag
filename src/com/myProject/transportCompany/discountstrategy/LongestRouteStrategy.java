package com.myProject.transportCompany.discountstrategy;

import com.myProject.transportCompany.generatemodels.AutoOrderManager;
import com.myProject.transportCompany.generatemodels.RouteManager;
import com.myProject.transportCompany.model.Location;
import com.myProject.transportCompany.model.Order;
import com.myProject.transportCompany.model.Route;

import java.util.List;

public class LongestRouteStrategy implements RouteStrategy{

    @Override
    public void chooseRoute(Order order) {
        System.out.println("Long but cheap delivery");
        Route route = order.getRoute();
        Location start =  route.getStart();
        Location end = route.getDestination();
        List<Location> inter = RouteManager.getRouteInstance().getCities(start, end);

        order.getRoute().setIntermediateCities(inter);
//        System.out.println("with new " + order.toString());
        System.out.println("order pricce: " + order.getPrice());
        order.setPrice(order.getRoute().getDistance() / 10.0);
        System.out.println("order pricce: " + order.getPrice());

        AutoOrderManager.getInstance().completeOrder(order);
    }
}
