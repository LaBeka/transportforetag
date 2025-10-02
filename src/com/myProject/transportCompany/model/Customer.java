package com.myProject.transportCompany.model;

public class Customer {
    private String name;
    private String destinationInfo;
    private String packageInfo;
    private double weight;
    private boolean isServed;

    public Customer(String name, String destinationInfo, String packageInfo, double weight) {
        this.name = name;
        this.destinationInfo = destinationInfo;
        this.packageInfo = packageInfo;
        this.weight = weight;
        this.isServed = false;
    }

    public String getDestinationInfo() {
        return destinationInfo;
    }

    public void setDestinationInfo(String destinationInfo) {
        this.destinationInfo = destinationInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPackageInfo() {
        return packageInfo;
    }

    public void setPackageInfo(String packageInfo) {
        this.packageInfo = packageInfo;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public boolean isServed() {
        return isServed;
    }

    public void setServed(boolean isServed) {
        this.isServed = isServed;
    }

    @Override
    public String toString() {
        return name + ", packageInfo: '" + packageInfo + "'" + ", weight " + weight;
    }
}
