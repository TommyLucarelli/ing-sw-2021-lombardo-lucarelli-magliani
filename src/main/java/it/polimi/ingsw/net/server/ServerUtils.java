package it.polimi.ingsw.net.server;

import it.polimi.ingsw.core.controller.PlayerHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Class containing public attributes such as the registered usernames and the number of clients connected.
 * @author Giacomo Lombardo
 */
public class ServerUtils {
    /**
     * Set of the registered usernames.
     */
    public static Set<String> usernames = new HashSet<>();

    /**
     * Number of clients connected.
     */
    public static int numClients = 0;

    public static int totalClients = 0;

    public static ArrayList<Lobby> lobbies = new ArrayList<>();

    public static HashMap<String, PlayerHandler> disconnectedPlayers = new HashMap();
}
