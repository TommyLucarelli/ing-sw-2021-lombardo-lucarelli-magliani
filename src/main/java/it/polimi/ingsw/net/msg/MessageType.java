package it.polimi.ingsw.net.msg;

public enum MessageType {
    /**
     * Only for testing, to enter an endless loop (until quit) of req-res of testing messages.
     */
    TESTING_MESSAGE,

    /**
     * To signal the dropping of the communication from the client
     */
    QUIT_MESSAGE,

    /**
     * Ping message
     */
    PING,

    /**
     * Message sent by the client to start the communication
     */
    FIRST_MESSAGE,

    /**
     * Message asking for username
     */
    REGISTRATION_MESSAGE,

    /**
     * Ask to create/join lobby
     */
    WELCOME_MESSAGE,

    /**
     * Specify the game's number of players
     */
    NUMBER_OF_PLAYERS,

    /**
     * Join lobby message
     */
    JOIN_GAME,

    /**
     * Wait for start game message
     */
    WAIT_START_GAME,

    /**
     * Start game message
     */
    START_GAME,

    /**
     * Game-related message
     */
    GAME_MESSAGE,

    /**
     * Reconnection message
     */
    RECONNECTION_MESSAGE,
}
