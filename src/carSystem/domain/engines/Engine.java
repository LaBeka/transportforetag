package carSystem.domain.engines;


import carSystem.domain.types.EngineType;

public class Engine {
    private String type;
    private int horsePower;
    private EngineType engineType;

    private boolean isOn = false;

    private Engine(Builder builder) {
        this.type = builder.type;
        this.horsePower = builder.horsePower;
        this.engineType = builder.engineType;
    }

    public static class Builder {
        private String type;
        private int horsePower;
        private EngineType engineType;

        public Builder type(String type) {
            this.type = type;
            return this;
        }

        public Builder horsepower(int horsePower) {
            this.horsePower = horsePower;
            return this;
        }

        public Builder fuelType(EngineType engineType) {
            this.engineType = engineType;
            return this;
        }

        public Engine build() {
            return new Engine(this);
        }
    }

    @Override
    public String toString() {
        return type + " Engine (" + horsePower + " HP, " + engineType + ")";
    }

    public void start(){
        if(!isOn){
            System.out.println("Engine started");
            isOn = true;
        }
    }

    public void stop(){
        if(isOn){
            System.out.println("Engine stopped");
            isOn = false;
        }
    }

}

