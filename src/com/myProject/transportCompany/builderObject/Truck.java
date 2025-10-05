package com.myProject.transportCompany.builderObject;


public class Truck extends Vehicle {

    private double capacity;

    public Truck(Builder builder) {
        super(builder);
        this.capacity = builder.capacity;
    }

    @Override
    public String toString() {
        return super.toString() + " and its capacity: " + capacity;
    }

    @Override
    public String getType() {
        return "Truck";
    }

    public double getCapacity() {
        return capacity;
    }

    public void setCapacity(double capacity) {
        this.capacity += capacity;
    }

    public static class Builder extends Vehicle.Builder<Truck, Builder> {
        private double capacity;

        public Builder capacity(double capacity) {
            this.capacity = capacity;
            return this;
        }

        public Builder isAvailable(boolean isAvailable) {
            if(isAvailable) {
                if(capacity < 100.0){
                    this.isAvailable = false;
                } else {
                    this.isAvailable = isAvailable;
                }
            } else {
                this.isAvailable = false;
            }
            return this;
        }

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
