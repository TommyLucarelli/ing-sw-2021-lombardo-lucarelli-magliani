package it.polimi.ingsw.net.client;

public enum Color {

    BLUE("\033[38;5;21m"),
    WHITE("\u001b[37m");

    private String color;

    Color(String color) {
        this.color = color;
    }
}