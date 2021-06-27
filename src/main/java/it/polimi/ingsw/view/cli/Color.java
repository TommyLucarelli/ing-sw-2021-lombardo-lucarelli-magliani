package it.polimi.ingsw.view.cli;

public enum Color {
    //Normal Colors
    BLACK("\033[0;30m"),   // BLACK
    RED("\033[0;31m"),     // RED
    GREEN("\033[0;32m"),   // GREEN
    YELLOW("\033[0;33m"),  // YELLOW
    BLUE("\033[0;34m"),    // BLUE
    PURPLE("\033[0;35m"),  // PURPLE
    HEAVENLY("\033[0;36m"),    // HEAVENLY
    WHITE("\033[0;37m"),   // WHITE

    // Bold
    BLACK_BOLD("\033[1;30m"),  // BLACK
    RED_BOLD("\033[1;31m"),    // RED
    GREEN_BOLD("\033[1;32m"),  // GREEN
    YELLOW_BOLD("\033[1;33m"), // YELLOW
    BLUE_BOLD("\033[1;34m"),   // BLUE
    PURPLE_BOLD("\033[1;35m"), // PURPLE
    HEAVENLY_BOLD("\033[1;36m"),   // HEAVENLY
    WHITE_BOLD("\033[1;37m"),  // WHITE
    BLACK_UNDERLINED("\033[4;30m"),
    WHITE_UNDERLINED("\033[4;37m"),
    RED_UNDERLINED("\033[4;31m"),

    LIME("\33[38;5;112m"),
    SUNSET("\33[38;5;210m"),
    SUNRISE("\33[38;5;229m"),
    MIDNIGHT("\33[38;5;20m"),
    PURPLE_PUNCH("\33[38;5;56m"),
    SMALL_FIRE("\33[38;5;134m"),
    BIG_FIRE("\033[38;5;196m");

    static final String RESET = "\u001B[0m";

    private String color;

    Color(String color) {
        this.color = color;
    }

    public String color(){
        return color;
    }

}