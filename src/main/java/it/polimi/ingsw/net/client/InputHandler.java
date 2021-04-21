package it.polimi.ingsw.net.client;

import com.google.gson.JsonObject;

import java.util.Scanner;

public class InputHandler {
    private static final Scanner scanner = new Scanner(System.in);

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

    private static int getInt(int min, int max){
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

    private static int getChoice(String[] choices){
        return 0;
    }

    private static String getString(String regex){
        String input;
        if(!regex.isBlank()){
            input = "";
            //TODO: gestire regex
        } else {
            input = scanner.nextLine();

        }
        return input;
    }
}
