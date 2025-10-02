package com.myProject.transportCompany.discountstrategy;

import com.myProject.transportCompany.generatemodels.OrderManager;
import com.myProject.transportCompany.model.Order;
import com.myProject.transportCompany.model.Route;

import java.util.Comparator;
import java.util.List;

public class ShortestRouteStrategy implements RouteStrategy{
    @Override
    public Route chooseRoute(List<Route> routes) {
        return routes.stream().min(Comparator.comparingDouble(Route::getDistance)).orElse(null);
    }

    @Override
    public void chooseRoute(Order order) {
        Route route = order.getRoute();
        double newValue = (order.getPrice() * 10.0);
        order.setPrice(newValue);
        OrderManager.getInstance().updateCurrentOrder(order);
        System.out.println(route.toString() + " for the price " + order.getPrice());
        System.out.println("Fast but Expensive Choose Route");
    }
}
