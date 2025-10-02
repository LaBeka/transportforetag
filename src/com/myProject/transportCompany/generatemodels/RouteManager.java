package com.myProject.transportCompany.generatemodels;

import com.myProject.transportCompany.InputHandler;
import com.myProject.transportCompany.interfaces.IRouteManager;
import com.myProject.transportCompany.model.Location;
import com.myProject.transportCompany.model.Order;
import com.myProject.transportCompany.discountstrategy.LongestRouteStrategy;
import com.myProject.transportCompany.discountstrategy.ShortestRouteStrategy;

import java.util.*;
import java.util.stream.Collectors;

public class RouteManager implements IRouteManager {

    private static volatile RouteManager routeInstance;
    private List<Location> cities = Arrays.asList(
            new Location("Stockholm"),
            new Location("Gothenburg"),
            new Location("Malmo"),
            new Location("Uppsala"),
            new Location("Orebro"));
    private Map<String, Double> distancStraightTwoCities;
    private List<String> routeKeys;
    private Map<String, List<Location>> intermediateCities;

    private RouteManager() { init(); }

    public static RouteManager getRouteInstance() {
        if (routeInstance == null) {
            synchronized (RouteManager.class) {
                if (routeInstance == null) {
                    routeInstance = new RouteManager();
                }
            }
        }
        return routeInstance;
    }

    @Override
    public void init(){
        this.routeKeys = new ArrayList<>();
        this.distancStraightTwoCities = new HashMap<>();
        this.intermediateCities  = new HashMap<>();
        generateStraightDistances();
    }

    @Override
    public double calculateDistance(Location start, List<Location> list, Location end) {
        List<Location> some = new ArrayList<>();
        some.add(start);
        some.addAll(list);
        some.add(end);
        Double d = 0.0;
        for (int i = 1; i < some.size(); i++) {
            Location begin = some.get(i-1);
            Location next = some.get(i);
            d += getStraightDistances(begin, next);
        }
        return d;
    }

    @Override
    public double getStraightDistances(Location start, Location end) {
        if(distancStraightTwoCities == null) init();

        String startKey = STR."\{start.getCity()} ↔ \{end.getCity()}";
        String endKey = STR."\{end.getCity()} ↔ \{start.getCity()}";

        Double distance= distancStraightTwoCities.get(startKey);

        if(distance == null){
            distance = distancStraightTwoCities.get(endKey);
        }
        System.out.println("Distance between " + start.getCity() + " and " + end.getCity() + " is " + distance);
        return distance;
    }

    @Override
    public void generateStraightDistances(){
        for (int i = 0; i < cities.size(); i++) {
            for (int j = i + 1; j < cities.size(); j++) {
                String key = cities.get(i).getCity() + " ↔ " + cities.get(j).getCity();
                routeKeys.add(key);
            }
        }
        for (int i = 0; i < routeKeys.size(); i++) {
            switch (routeKeys.get(i)) {
                case "Stockholm ↔ Gothenburg": distancStraightTwoCities.put("Stockholm ↔ Gothenburg", 470.0); break;
                case "Stockholm ↔ Malmo": distancStraightTwoCities.put("Stockholm ↔ Malmo", 615.0); break;
                case "Stockholm ↔ Uppsala": distancStraightTwoCities.put("Stockholm ↔ Uppsala", 70.0); break;
                case "Stockholm ↔ Orebro": distancStraightTwoCities.put("Stockholm ↔ Orebro", 170.0); break;
                case "Gothenburg ↔ Malmo": distancStraightTwoCities.put("Gothenburg ↔ Malmo", 270.0); break;
                case "Gothenburg ↔ Uppsala": distancStraightTwoCities.put("Gothenburg ↔ Uppsala", 440.0); break;
                case "Gothenburg ↔ Orebro": distancStraightTwoCities.put("Gothenburg ↔ Orebro", 380.0); break;
                case "Malmo ↔ Uppsala": distancStraightTwoCities.put("Malmo ↔ Uppsala", 610.0); break;
                case "Malmo ↔ Orebro": distancStraightTwoCities.put("Malmo ↔ Orebro", 530.0); break;
                case "Uppsala ↔ Orebro": distancStraightTwoCities.put("Uppsala ↔ Orebro", 120.0); break;
            }
        }
    }

    @Override
    public void generateIntermediateCities(Location start, Location end) {
        String route = start.getCity() + " ↔ " + end.getCity();
        switch (route) {
            case "Stockholm ↔ Malmo":
                intermediateCities.put(routeKeys.get(1), Arrays.asList(start, cities.get(3), cities.get(4), cities.get(2), end));
                break;
            case "Stockholm ↔ Gothenburg":
                intermediateCities.put(routeKeys.get(0), Arrays.asList(start, cities.get(2), cities.get(1), cities.get(4), end));
                break;
        }
    }

    public List<Location> getCities(Location start, Location end) {
        List<Location> intermediateCities = new ArrayList<>(cities);
//        removes start and end cities.
        intermediateCities.removeIf(
                location -> location.getCity().equals(end.getCity()) || location.getCity().equals(start.getCity())
        );
        Collections.shuffle(intermediateCities);

        // Return up to 3 cities (could be less if only 1–2 are left after exclusion)
        return intermediateCities.stream().limit(3).collect(Collectors.toList());
    }
    @Override
    public void negotiatingMenu(Order order) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Runnable> orderMenu = new LinkedHashMap<>();
        orderMenu.put("Do you still want the fastest and expensive delivery", () -> expensiveOrder(order));
        orderMenu.put("Or the cheapest and slow order: ", () -> cheapOrder(order));

        InputHandler.runMainMenu(scanner, orderMenu);
    }

    @Override
    public void expensiveOrder(Order order) {
        order.chooseStrategy(new ShortestRouteStrategy());
    }

    @Override
    public void cheapOrder(Order order) {
        order.chooseStrategy(new LongestRouteStrategy());
    }
}
