package it.polimi.ingsw.view.cli;

import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * Class that handles the input operations.
 */
public class InputHandler {
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Main method used to get the user input.
     * @param expectedResponse a JsonObject containing the information on the desired input (e.g. a integer between 1
     *                         and 4, a string of at least 4 characters)
     * @return a JsonObject containing the user input.
     */
    public static JsonObject getInput(JsonObject expectedResponse){
        JsonObject payload = new JsonObject();

        switch (expectedResponse.get("type").getAsString()){
            case "int":
                payload.addProperty("input", getInt(
                        expectedResponse.has("min") ? expectedResponse.get("min").getAsInt() : -1,
                        expectedResponse.has("max") ? expectedResponse.get("max").getAsInt() : -1));
                break;
            case "string":
                payload.addProperty("input", getString(
                        expectedResponse.has("regex") ? expectedResponse.get("regex").getAsString() : ""));
                break;
            default:
                payload.addProperty("input", getString(""));
                break;
        }
        return payload;
    }

    /**
     * Method used to get an integer.
     * @param min if !=-1, the minimum accepted value.
     * @param max if !=-1, the maximum accepted value.
     * @return the user's input.
     */
    public static int getInt(int min, int max){
        boolean hasMin = min != -1;
        boolean hasMax = max != -1;
        int input;
        if(hasMin && hasMax){
            do {
                while (!scanner.hasNextInt()) {
                    System.out.println("Please enter a number:");
                    scanner.next();
                }
                input = scanner.nextInt();
                if(input < min || input > max)
                    System.out.println("Please enter a number between " + min + " and " + max + ":");
            } while (input < min || input > max);
        } else if(hasMin){
            do {
                while (!scanner.hasNextInt()) {
                    System.out.println("Please enter a number:");
                    scanner.next();
                }
                input = scanner.nextInt();
                if(input < min)
                    System.out.println("Please enter a number greater than " + min + ":");
            } while (input < min);
        } else if(hasMax){
            do {
                while (!scanner.hasNextInt()) {
                    System.out.println("Please enter a number:");
                    scanner.next();
                }
                input = scanner.nextInt();
                if(input > max)
                    System.out.println("Please enter a number smaller than " + max + ":");
            } while (input > max);
        } else {
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a number:");
                scanner.next();
            }
            input = scanner.nextInt();
        }
        return input;
    }

    /**
     * Method used to get an integer.
     * @return the user's input.
     */
    public static int getInt(){
        while (!scanner.hasNextInt()) {
            System.out.println("Please enter a number:");
            scanner.next();
        }
        return scanner.nextInt();
    }

    /**
     * Method used to get an integer from an arrayList of options.
     * @param array the arrayList of options.
     * @return the user's input.
     */
    public static int getIntFromArray(ArrayList<Integer> array){
        int input;
        do {
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a number:");
                scanner.next();
            }
            input = scanner.nextInt();
            if(!array.contains(input)){
                System.out.println("Please enter a number from the following array: " + array);
            }
        } while (!array.contains(input));
        return input;
    }

    /**
     * Method used to get a choice between various options.
     * @param choices the array of possible options.
     * @return the index of the chosen option.
     */
    private static int getChoice(int[] choices){
        return getInt(1, choices.length);
    }

    /**
     * Method used to get a choice between various options.
     * @param choices the array of possible options.
     * @return the index of the chosen option.
     */
    private static int[] getMultipleChoices(int[] choices, int howMany){
        ArrayList<Integer> inputs = new ArrayList<>();
        for (int i = 0; i < howMany; i++) {
            int input = getInt(1, choices.length);
            if(inputs.contains(input)){
                i--;
                System.out.println("You have already chosen this element!");
            }
            else inputs.add(input);
        }
        return inputs.stream().mapToInt(i -> i).toArray();
    }

    /**
     * Method used to get a string.
     * @param regex if !isBlank(), the regex pattern that the string needs to match.
     * @return the user's input.
     */
    public static String getString(String regex){
        String input;
        if(!regex.isBlank()){
            while (!scanner.hasNext(regex)) {
                System.out.println("Please enter a valid string.");
                scanner.next();
            }
            input = scanner.next();
        } else {
            input = scanner.nextLine();
        }
        return input;
    }

    public static String getString(){
        while(!scanner.hasNext()){
            scanner.next();
        }
        return scanner.nextLine();
    }
}
