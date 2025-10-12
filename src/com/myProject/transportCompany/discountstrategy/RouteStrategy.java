package com.myProject.transportCompany.discountstrategy;

import com.myProject.transportCompany.model.Order;
import com.myProject.transportCompany.model.Route;

import java.util.List;

public interface RouteStrategy {

    void chooseRoute(Order order);
}
