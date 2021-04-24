package it.polimi.ingsw.net.msg;

public enum MessageType {
    TESTING_MESSAGE, //Only for testing, to enter an endless loop (until quit) of req-res of testing messages.
    ERROR_MESSAGE, //To signal an incorrect communication/input
    QUIT_MESSAGE,  //To signal the dropping of the communication from the client
    REGISTRATION_MESSAGE, //Ask for username
    WELCOME_MESSAGE, //Ask to create/join lobby
    NUMBER_OF_PLAYERS, //Specify the game's number of players
    WAIT_FOR_PLAYERS,
    JOIN_GAME, //Join lobby
    WAIT_START_GAME, //Wait for start game
    START_GAME, //Start game
    GAME_MESSAGE,
}
