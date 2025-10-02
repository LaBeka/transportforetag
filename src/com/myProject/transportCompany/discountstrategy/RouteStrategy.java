package com.myProject.transportCompany.discountstrategy;

import com.myProject.transportCompany.model.Order;
import com.myProject.transportCompany.model.Route;

import java.util.List;

public interface RouteStrategy {
    Route chooseRoute(List<Route> routes);

    void chooseRoute(Order order);
}
