package carSystem;


import carSystem.domain.Car;
import carSystem.domain.Garage;
import carSystem.domain.engines.Engine;
import carSystem.initalization.AssignDriverToCar;
import carSystem.initalization.Populate;
import carSystem.logic.EngineCommand;
import carSystem.logic.*;

import java.util.Random;

public class Application {

    private final Random random = new Random();

    public void run(){

        init();
    }

    public void init(){

        //+Register new cars
        Populate data = new Populate();

        //+Connect drivers to cars
        AssignDriverToCar fleetService = new AssignDriverToCar();
        fleetService.assignDriversToCars(data.getCarList(), data.getDrivers());

        Garage garage = new Garage();

        for(Car car : data.getCarList()){
//            boolean placeInGarage = random.nextBoolean();
//            if(placeInGarage){
                garage.addCar(car);
//            }
        }

        //+Turn engines on/off
        for(Car currentCar : garage.getCars()){
            boolean startDriving = random.nextBoolean();

            if(startDriving){
                System.out.println("Started driving: " + currentCar.toString());
                EngineCommand invoker = new EngineCommand();
                invoker.setCommand(new StartEngine(currentCar));
                invoker.run();

                ServiceHistory log = new ServiceHistory();
                //Add service history
                log.addEntry("Oil change");
                log.addEntry("Tire rotation");
                log.printLog();

                FuelConsumption calc = new FuelConsumptionCalculator();
                System.out.println("Consumption: " + calc.calculate(500, 40) + " km/l");

                invoker.setCommand(new StopEngine(currentCar));
                invoker.run();

                System.out.println(currentCar.extendedString());
            } else {
                System.out.println(currentCar.toString());
            }
        }
        //Log fuel consumption per trip (extra challenge)
    }

}
