package com.myProject.transportCompany;

import java.util.*;

public class InputHandler {

    public static String runMainMenu(Scanner scanner, Map<String, Runnable> optionMap) {
        boolean[] flag = new boolean[] { true };
        String chosen = null;
        Map<String, Runnable> optionMapCopy = new LinkedHashMap<>(optionMap);
        optionMapCopy.put("Quit", () -> flag[0] = false );
        while (flag[0]) {
            chosen = runMenu(scanner, optionMapCopy);
        }
        return chosen;
    }

    public static String runMenu(Scanner scanner, Map<String, Runnable> optionMap) {
        List<String> keys = new ArrayList<>(optionMap.keySet());
        String chosenMenuItem = null;
        while (true) {
            // Print menu options with indexes
            for (int i = 0; i < keys.size(); i++) {
                System.out.println(i + ": " + keys.get(i));
            }

            System.out.print("Enter a number:  ");
            String input;
//                System.out.println("Chosen: " + input);
            try {
                if (!scanner.hasNextLine()) {
                    System.out.println("No input detected. Exiting menu.");
                    return null; // or break;
                }
                input = scanner.nextLine();
                int choice;
                choice = Integer.parseInt(input);
                if (choice >= 0 && choice < keys.size()) {
                    // Execute corresponding function
                    optionMap.get(keys.get(choice)).run();
                    chosenMenuItem = keys.get(choice);
                    break;
                } else {
                    System.out.println("Invalid choice. Please try again.\n");
                }
            } catch (NoSuchElementException | NumberFormatException el){
                System.out.println("Please enter a valid integer.\n");
                return null;
            }
        }
        return chosenMenuItem;
    }
}
