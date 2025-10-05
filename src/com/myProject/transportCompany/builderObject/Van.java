package com.myProject.transportCompany.builderObject;

public class Van extends Vehicle {

    public Van(Builder builder) {
        super(builder);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public String getType() {
        return "Van";
    }

    public static class Builder extends Vehicle.Builder<Van, Builder> {
        @Override
        protected Builder self() {
            return this;
        }

        public Builder isAvailable(boolean isAvailable) {
            this.isAvailable = isAvailable;
            return this;
        }

        @Override
        public Van build() {
            return new Van(this);
        }
    }
}
