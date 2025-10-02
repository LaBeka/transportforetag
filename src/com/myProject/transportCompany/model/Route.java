package com.myProject.transportCompany.model;

import com.myProject.transportCompany.generatemodels.RouteManager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Route {

    private Location start;
    private Location destination;
    private double distance;
    private List<Location> intermediateCities;

//    public Route(Location start, Location destination, List<Location> intermediateCities, double distance) {
//        this.start = start;
//        this.destination = destination;
//        this.intermediateCities = intermediateCities;
//        this.distance = distance;
//    }

    public Location getStart() {
        return start;
    }

    public void setStartingPoint(Location start) {
        this.start = start;
    }

    public Location getDestination() {
        return destination;
    }

    public void setDestination(Location destination) {
        this.destination = destination;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public List<Location> getIntermediateCities() {
        return intermediateCities;
    }

    public void setIntermediateCities(List<Location> intermediateCities) {
        this.intermediateCities = intermediateCities;
        setDistance(RouteManager.getRouteInstance().calculateDistance(start, this.intermediateCities, destination));
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        StringBuilder inter = new StringBuilder();
        if(!intermediateCities.isEmpty()) {
            inter.append(" via ");
            for(Location location : intermediateCities) {
                inter.append(location.getCity()).append(" ");
            }
        }
        return sb.append("Route from ").append(start.getCity()).append(" to ").append(destination.getCity())
                .append(inter).append(" is ").append(distance).append(" km").toString();
    }

    public String getString(){
        StringBuilder sb = new StringBuilder();
        for(Location location : intermediateCities) {
            sb.append(location.toString()).append(" ");
        }
        return sb.toString();
    }

    private Route(Builder builder) {
        this.start = builder.start;
        this.destination  = builder.destination;
        this.distance = builder.distance;
        this.intermediateCities = new ArrayList<>();

    }
    public static class Builder {
        private Location start;
        private Location destination;
        private double distance;


        public Builder start(Location start) {
            this.start = start;
            return this;
        }
        public Builder destination(Location destination) {
            this.destination = destination;
            return this;
        }
        public Builder distance(Location start, Location destination) {
            this.distance = RouteManager.getRouteInstance().getStraightDistances(start, destination);
            return this;
        }
//        public Builder intermediateCities() {
//            this.intermediateCities = intermediateCities;
//            return this;
//        }
        public Route build() {
            return new Route(this);
        }
    }


}
