package carSystem.logic;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceHistory {
    private List<String> logs =
            new ArrayList<String>();

    public void addEntry(String entry) {
        logs.add(LocalDate.now() + ": " + entry);
    }

    public void printLog() {
        logs.forEach(System.out::println);
    }
}
