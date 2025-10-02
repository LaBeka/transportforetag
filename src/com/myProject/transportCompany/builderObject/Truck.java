package com.myProject.transportCompany.builderObject;


public class Truck extends Vehicle {

    public Truck(Builder builder) {
        super(builder);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String getType() {
        return "Truck";
    }

    public static class Builder extends Vehicle.Builder<Truck, Builder> {
        @Override
        protected Builder self() {
            return this;
        }

        @Override
        public Truck build() {
            return new Truck(this);
        }
    }
}
