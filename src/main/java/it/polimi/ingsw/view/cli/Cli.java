package it.polimi.ingsw.view.cli;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import it.polimi.ingsw.core.model.*;
import it.polimi.ingsw.net.client.Client;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.UserInterface;
import it.polimi.ingsw.view.compact.*;

import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Main class for CLI operations.
 */
public class Cli implements UserInterface {
    private final Client client;
    private CompactPlayer mySelf;
    private CardCollector cardCollector;
    private final FancyPrinter fancyPrinter;
    private CompactMarket compactMarket;
    private CompactDevCardStructure compactDevCardStructure;
    private HashMap<Integer, CompactPlayer> opponents;

    /**
     * Class constructor.
     * @param client the client socket used to send messages to the server.
     */
    public Cli(Client client) throws FileNotFoundException {
        this.client = client;
        cardCollector = new CardCollector();
        fancyPrinter = new FancyPrinter();
        opponents = new HashMap<>();
    }

    @Override
    public void handleRequest(RequestMsg request){
        switch(request.getMessageType()){
            case REGISTRATION_MESSAGE:
            case WELCOME_MESSAGE:
            case JOIN_GAME:
            case NUMBER_OF_PLAYERS:
            case START_GAME:
            case RECONNECTION_MESSAGE:
                handleSimpleRequest(request);
                break;
            case WAIT_START_GAME:
                ackSimpleRequest(request);
                break;
            case GAME_MESSAGE:
                switch (request.getPayload().get("gameAction").getAsString()){
                    case "START_GAME_COMMAND":
                        handleSimpleRequest(request);
                        break;
                    case "START_TURN":
                        handleNotMyTurn(request);
                        break;
                    case "WAIT_FOR_PLAYERS":
                    case "WAIT_START_GAME":
                    case "SHORT_UPDATE":
                        ackSimpleRequest(request);
                        break;
                    case "CHOOSE_START_LEADERS":
                        handleChooseStartLeaders(request);
                        break;
                    case "CHOOSE_START_RESOURCES":
                        handleChooseStartResources(request);
                        break;
                    case "INITIAL_UPDATE":
                        handleInitialUpdate(request);
                        break;
                    case "LEADER_ACTIVATION":
                        handleLeaderActivation(request);
                        break;
                    case "LEADER_ACTION":
                        handleLeaderAction(request);
                        break;
                    case "MAIN_CHOICE":
                        handleMainChoice(request);
                        break;
                    case "CHOOSE_DEVCARD":
                        handleChooseDevCard(request);
                        break;
                    case "DEVCARD_PLACEMENT":
                        handleDevCardPlacement(request);
                        break;
                    case "PICK":
                        handlePick(request);
                        break;
                    case "WAREHOUSE_PLACEMENT":
                        handleWarehousePlacement(request);
                        break;
                    case "CHOOSE_PRODUCTION":
                        handleChooseProduction(request);
                        break;
                    case "UPDATE":
                        handleUpdate(request);
                        break;
                    case "FINAL_UPDATE":
                        handleFinalUpdate(request);
                        break;
                    case "RECONNECTION_UPDATE":
                        handleReconnectionUpdate(request);
                        break;
                }
                break;
            default:
                break;
        }
    }


    /**
     * Method used to handle "simple" requests from the server: a simple request consists in a message and an expected
     * response such as a number or a string, with no collateral effects on the client.
     * @param requestMsg the request sent by the server.
     */
    private void handleSimpleRequest(RequestMsg requestMsg) {
        System.out.println(requestMsg.getPayload().get("message").getAsString());
        JsonObject payload = InputHandler.getInput(requestMsg.getPayload().getAsJsonObject("expectedResponse"));
        if(requestMsg.getMessageType() == MessageType.GAME_MESSAGE)
            payload.addProperty("gameAction", requestMsg.getPayload().get("gameAction").getAsString());
        client.send(new ResponseMsg(requestMsg.getIdentifier(), requestMsg.getMessageType(), payload));
    }

    /**
     * Method used to handle "simple" requests from the server which need only an acknowledgment. These requests consist
     * of a simple text message with no collateral effects.
     * @param requestMsg the request sent by the server.
     */
    private void ackSimpleRequest(RequestMsg requestMsg) {
        System.out.println(requestMsg.getPayload().get("message").getAsString());
        if(requestMsg.getMessageType() == MessageType.GAME_MESSAGE){
            JsonObject payload = new JsonObject();
            payload.addProperty("gameAction", requestMsg.getPayload().get("gameAction").getAsString());
            client.send(new ResponseMsg(requestMsg.getIdentifier(), requestMsg.getMessageType(), payload));
        } else {
            client.send(new ResponseMsg(requestMsg.getIdentifier(), requestMsg.getMessageType(), null));
        }
    }

    /**
     * Method used to handle the phase of selecting the two leader cards to begin the game with.
     * @param requestMsg the request sent by the server.
     */
    private void handleChooseStartLeaders(RequestMsg requestMsg){
        LeaderCard lc;
        int x, y, j=0, k=0;
        int[] discardedLeaders = new int[2];
        int[] leaders = new int[2];
        int playerID = requestMsg.getPayload().get("playerID").getAsInt();
        String playerName = requestMsg.getPayload().get("playerName").getAsString();
        mySelf = new CompactPlayer(playerID, playerName);

        Gson gson = new Gson();
        String json = requestMsg.getPayload().get("leaderCards").getAsString();
        ArrayList<Integer> leaderCards = gson.fromJson(json, new TypeToken<ArrayList<Integer>>(){}.getType());


        System.out.println("\n" +
                "  __  __           _                     __   ____                  _                              \n" +
                " |  \\/  | __ _ ___| |_ ___ _ __    ___  / _| |  _ \\ ___ _ __   __ _(_)___ ___  __ _ _ __   ___ ___ \n" +
                " | |\\/| |/ _` / __| __/ _ \\ '__|  / _ \\| |_  | |_) / _ \\ '_ \\ / _` | / __/ __|/ _` | '_ \\ / __/ _ \\\n" +
                " | |  | | (_| \\__ \\ ||  __/ |    | (_) |  _| |  _ <  __/ | | | (_| | \\__ \\__ \\ (_| | | | | (_|  __/\n" +
                " |_|  |_|\\__,_|___/\\__\\___|_|     \\___/|_|   |_| \\_\\___|_| |_|\\__,_|_|___/___/\\__,_|_| |_|\\___\\___|\n" +
                "                                                                                                   \n");

        System.out.println("");
        System.out.println("\nThe game has started!!");
        if(requestMsg.getPayload().has("playerOrder")){
            System.out.println("You are player "+requestMsg.getPayload().get("playerOrder").getAsInt());
        }else{
            System.out.println("You are in single player mode, your opponent is Lorenzo il Magnifico himself");
        }


        int[] printLeaders = new int[4];
        for (int i = 0; i < leaderCards.size(); i++) {
            printLeaders[i] = leaderCards.get(i);
        }
        fancyPrinter.printArrayLeaderCard(printLeaders);
        System.out.println("\t\t\t  1\t\t\t\t\t\t\t  2\t\t\t\t\t\t\t  3\t\t\t\t\t\t\t  4");

        //TODO: controllo
        System.out.println("\nChoose your first leader card");
        x = InputHandler.getInt(1, 4) - 1;
        leaders[0] = leaderCards.get(x);
        leaderCards.remove(x);

        printLeaders = new int[3];
        for (int i = 0; i < leaderCards.size(); i++) {
            printLeaders[i] = leaderCards.get(i);
        }
        fancyPrinter.printArrayLeaderCard(printLeaders);
        System.out.println("\t\t\t  1\t\t\t\t\t\t\t  2\t\t\t\t\t\t\t  3");

        //TODO: controllo
        System.out.println("\nChoose your second leader card");
        x = InputHandler.getInt(1, 3) - 1;
        leaders[1] = leaderCards.get(x);
        leaderCards.remove(x);

        for (int i: leaderCards) {
            discardedLeaders[j] = i;
            j++;
        }


        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "CHOOSE_START_LEADERS");
        payload.addProperty("playerID", mySelf.getPlayerID());
        json = gson.toJson(discardedLeaders); //forse sarebbe meglio trasformarlo in array
        payload.addProperty("discardedLeaders", json);

        client.send(new ResponseMsg(requestMsg.getIdentifier(), MessageType.GAME_MESSAGE, payload));
    }

    /**
     * Method used to handle the phase of selecting the resources to begin the game with.
     * @param requestMsg the request sent by the server.
     */
    private void handleChooseStartResources(RequestMsg requestMsg){
        int x, y, n;
        Resource[] placed1 = new Resource[10];
        for (int i = 0; i < 10; i++) {
            placed1[i] = Resource.ANY;
        }

        Resource a, b;
        if(requestMsg.getPayload().get("resources").getAsInt() == 0)
            System.out.println("\nWait for the other players to finish their initial turn");
        else{
            x = requestMsg.getPayload().get("resources").getAsInt();
            y = requestMsg.getPayload().get("faithPoints").getAsInt();
            System.out.println("\nYou are entitled to "+x+" resources ");
            if(y!=0)
                System.out.println("and "+y+" faith points");

            System.out.println("\nChoose a resource:");
            System.out.println("1. COIN");
            System.out.println("2. STONE");
            System.out.println("3. SHIELD");
            System.out.println("4. SERVANT");

            //TODO: controllo
            n = InputHandler.getInt(1,4);
            a = Resource.values()[n-1];
            if(x == 2){
                System.out.println("\nChoose another resource:");
                n = InputHandler.getInt(1,4);
                b = Resource.values()[n-1];
                if(a.equals(b)){
                    placed1[1] = a;
                    placed1[2] = b;
                }else{
                    placed1[0] = a;
                    placed1[1] = b;
                }
            }else{
                placed1[0] = a;
            }

            System.out.println("The Resources have been placed");
            System.out.println("\nWait for the other players to finish their initial turn");

            JsonObject payload = new JsonObject();
            payload.addProperty("gameAction", "CHOOSE_START_RESOURCES");
            payload.addProperty("playerID", mySelf.getPlayerID());
            Gson gson = new Gson();
            String json = gson.toJson(new ArrayList<>(Arrays.asList(placed1))); //forse sarebbe meglio trasformarlo in array
            payload.addProperty("placed", json);

            client.send(new ResponseMsg(requestMsg.getIdentifier(), MessageType.GAME_MESSAGE, payload));
        }
    }

    /**
     * Method used to handle the initial update sent by the server to initialize the client-side model structures.
     * @param requestMsg the request sent by the server.
     */
    private void handleInitialUpdate(RequestMsg requestMsg){
        compactMarket = new CompactMarket();
        compactDevCardStructure = new CompactDevCardStructure();
        int nextPlayerID;

        JsonObject payload = requestMsg.getPayload().get("market").getAsJsonObject();
        nextPlayerID = requestMsg.getPayload().get("nextPlayerID").getAsInt();
        Gson gson = new Gson();
        String json = payload.get("structure").getAsString();
        Type collectionType = new TypeToken<int[]>(){}.getType();
        int[] structure = gson.fromJson(json, collectionType);
        compactMarket.setMarket(structure);

        payload = requestMsg.getPayload().get("devCardStructure").getAsJsonObject();
        json = payload.get("structure").getAsString();
        collectionType = new TypeToken<int[][]>(){}.getType();
        int[][] structure2 = gson.fromJson(json, collectionType);
        compactDevCardStructure.setDevCardStructure(structure2);

        JsonObject payload2;
        JsonArray players = requestMsg.getPayload().get("players").getAsJsonArray();
        for (JsonElement player: players) {
            if(player.getAsJsonObject().get("playerID").getAsInt() == mySelf.getPlayerID()){
                payload2 = player.getAsJsonObject().get("faithTrack").getAsJsonObject();
                mySelf.getCompactBoard().setFaithTrackIndex(payload2.get("index").getAsInt());
                json = payload2.get("favCards").getAsString();
                collectionType = new TypeToken<boolean[]>(){}.getType();
                boolean[] fav = gson.fromJson(json, collectionType);
                mySelf.getCompactBoard().setFavCards(fav);

                payload2 = player.getAsJsonObject().get("warehouse").getAsJsonObject();
                json = payload2.get("structure").getAsString();
                collectionType = new TypeToken<Resource[]>(){}.getType();
                Resource[] ware = gson.fromJson(json, collectionType);
                mySelf.getCompactBoard().setWarehouse(ware);

                json = player.getAsJsonObject().get("leaderCards").getAsString();
                collectionType = new TypeToken<int[]>(){}.getType();
                int[] lcs = gson.fromJson(json, collectionType);
                mySelf.getCompactBoard().setLeaderCards(lcs);

            }else{
                opponents.put(player.getAsJsonObject().get("playerID").getAsInt(), new CompactPlayer(player.getAsJsonObject().get("playerID").getAsInt(),player.getAsJsonObject().get("playerName").getAsString()));

                opponents.get(player.getAsJsonObject().get("playerID").getAsInt()).getCompactBoard().setOpponent(true);
                
                payload2 = player.getAsJsonObject().get("faithTrack").getAsJsonObject();
                opponents.get(player.getAsJsonObject().get("playerID").getAsInt()).getCompactBoard().setFaithTrackIndex(payload2.get("index").getAsInt());
                json = payload2.get("favCards").getAsString();
                collectionType = new TypeToken<boolean[]>(){}.getType();
                boolean[] fav = gson.fromJson(json, collectionType);
                opponents.get(player.getAsJsonObject().get("playerID").getAsInt()).getCompactBoard().setFavCards(fav);

                payload2 = player.getAsJsonObject().get("warehouse").getAsJsonObject();
                json = payload2.get("structure").getAsString();
                collectionType = new TypeToken<ArrayList<Resource>>(){}.getType();
                ArrayList<Resource> ware = gson.fromJson(json, collectionType);
                Resource[] wa = new Resource[10];
                wa = ware.toArray(wa);
                opponents.get(player.getAsJsonObject().get("playerID").getAsInt()).getCompactBoard().setWarehouse(wa);

                json = player.getAsJsonObject().get("leaderCards").getAsString();
                collectionType = new TypeToken<int[]>(){}.getType();
                int[] lcs = gson.fromJson(json, collectionType);
                opponents.get(player.getAsJsonObject().get("playerID").getAsInt()).getCompactBoard().setLeaderCards(lcs);
            }
        }


        JsonObject payload3 = new JsonObject();
        payload3.addProperty("gameAction", "INITIAL_UPDATE");
        payload3.addProperty("playerID", mySelf.getPlayerID());
        client.send(new ResponseMsg(requestMsg.getIdentifier(), MessageType.GAME_MESSAGE, payload3));
    }

    /**
     * Method used to handle the decision of activating/discarding a leader card at the beginning/end of the turn.
     * @param requestMsg the request sent by the server.
     */
    private void handleLeaderActivation(RequestMsg requestMsg){

        if(!requestMsg.getPayload().get("endTurn").getAsBoolean()){
            System.out.println("\nYOUR PERSONAL BOARD: \n");
            fancyPrinter.printPersonalBoard(mySelf.getCompactBoard());
            if(opponents.size()==0){
                fancyPrinter.printBlackFaithTrack(mySelf.getCompactBoard());
            }
        }

        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "LEADER_ACTIVATION");

        System.out.println("\nDo you want to activate or discard a Leader Card? [yes/no]");
        String answer = InputHandler.getString("(yes|no)");
        if(answer.equals("yes"))
            payload.addProperty("activation", true);
        else if(answer.equals("no"))
            payload.addProperty("activation", false);

        client.send(new ResponseMsg(requestMsg.getIdentifier(), MessageType.GAME_MESSAGE, payload));
    }

    /**
     * Method used to handle the phase of activating/discarding a leader card at the beginning/end of the turn.
     * @param requestMsg the request sent by the server.
     */
    private void handleLeaderAction(RequestMsg requestMsg){
        JsonObject payload = new JsonObject();
        int x, cont = 0;
        boolean flag;

        if(requestMsg.getPayload().has("problem"))
        if(requestMsg.getPayload().has("problem"))
            System.out.println("\nOperation failed, retry or skip action");

        System.out.println("\nChoose a Leader Card to activate or discard: ");
        for (int i = 0; i < 2; i++) {
            flag = false;
            for (int j = 0; j < 8; j++) {
                if(mySelf.getCompactBoard().getAbilityActivationFlag()[j] == mySelf.getCompactBoard().getLeaderCards()[i])
                    flag = true;
            }
            if(mySelf.getCompactBoard().getLeaderCards()[i] != 0 && !flag){
                cont++;
                System.out.println("\n"+cont+".");
                fancyPrinter.printLeaderCard(mySelf.getCompactBoard().getLeaderCards()[i]);
            }
        }
        if(cont == 0){
            System.out.println("\nYou had activated or discarded all your leader cards!!");
            payload.addProperty("gameAction", "COME_BACK");
        }else {
            System.out.println("\n" + (cont+1) + ". to move on to the main action of the turn");
            x = InputHandler.getInt(1, cont+1);
            if (x == cont - 1) {
                payload.addProperty("gameAction", "LEADER_ACTION");
                payload.addProperty("cardID", mySelf.getCompactBoard().getLeaderCards()[0]);
            } else if (x == cont) {
                payload.addProperty("gameAction", "LEADER_ACTION");
                payload.addProperty("cardID", mySelf.getCompactBoard().getLeaderCards()[1]);
            } else if (x == cont+1)
                payload.addProperty("gameAction", "COME_BACK");

            if (x == cont -1 || x == cont) {
                System.out.println("\nWhich action do you want to perform:");
                System.out.println("1. Activate");
                System.out.println("2. Discard");
                x = InputHandler.getInt(1, 2);
                if (x == 1)
                    payload.addProperty("action", true);
                else if (x == 2)
                    payload.addProperty("action", false);
            }
        }
        client.send(new ResponseMsg(requestMsg.getIdentifier(), MessageType.GAME_MESSAGE, payload));
    }

    /**
     * Method used to handle the decision of the main action to perform during the turn.
     * @param requestMsg the request sent by the server.
     */
    private void handleMainChoice(RequestMsg requestMsg) {
        int x;
        String json = "";

        Gson gson = new Gson();
        if(requestMsg.getPayload().has("abilityActivationFlag")){
            json = requestMsg.getPayload().get("abilityActivationFlag").getAsString();
            Type collectionType = new TypeToken<int[]>(){}.getType();
            mySelf.getCompactBoard().setAbilityActivationFlag(gson.fromJson(json, collectionType));
        }


        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "MAIN_CHOICE");

        System.out.println("\nWhich action you want to do: ");
        System.out.println("1. Pick resources from market");
        System.out.println("2. Buy a development card");
        System.out.println("3. Activate production");
        x = InputHandler.getInt(1, 3);
        if(x == 1)
            payload.addProperty("actionChoice", "market");
        else if(x == 2)
            payload.addProperty("actionChoice", "buyDevCard");
        else if(x == 3)
            payload.addProperty("actionChoice", "production");

        client.send(new ResponseMsg(requestMsg.getIdentifier(), MessageType.GAME_MESSAGE, payload));
    }

    /**
     * Method used to handle the action of buying a card from the development card structure.
     * @param requestMsg the request sent by the server.
     */
    private void handleChooseDevCard(RequestMsg requestMsg){
        int l=0, c=0;
        boolean flag = false;
        Resource r1, r2;
        JsonObject payload = new JsonObject();

        if(requestMsg.getPayload().has("problem"))
            System.out.println("\nOperation failed, retry or skip action");

        System.out.println("Choose a development card: ");
        //discount da sistemare esteticamente
        System.out.println("Available discount: ");
        if(mySelf.getCompactBoard().getAbilityActivationFlag()[4] != 0){
            r1 = cardCollector.getLeaderCard(mySelf.getCompactBoard().getAbilityActivationFlag()[4]).getSpecialAbility().getAbilityResource();
            System.out.println(r1.toString());
            flag = true;
        }
        if(mySelf.getCompactBoard().getAbilityActivationFlag()[5] != 0){
            r2 = cardCollector.getLeaderCard(mySelf.getCompactBoard().getAbilityActivationFlag()[4]).getSpecialAbility().getAbilityResource();
            System.out.println(r2.toString());
            flag = true;
        }
        if(!flag)
            System.out.println("None");

        fancyPrinter.printDevCardStructure(compactDevCardStructure);
        System.out.println("\nChoose level: (4-comeback to main choice)");
        l = InputHandler.getInt(1,4)-1;

        if(l==3){
            payload.addProperty("gameAction", "COME_BACK");
        }else {
            System.out.println("\nChoose color (green/blue/yellow/purple)");
            String s = InputHandler.getString("(green|blue|yellow|purple)");
            switch (s) {
                case "green":
                    c = 0;
                    break;
                case "blue":
                    c = 1;
                    break;
                case "yellow":
                    c = 2;
                    break;
                case "purple":
                    c = 3;
                    break;
            }

            System.out.println("1. confirm purchase");
            System.out.println("2. comeback to main choice");
            int m = InputHandler.getInt(1, 2);
            if (m == 1) {
                payload.addProperty("gameAction", "CHOOSE_DEVCARD");
                payload.addProperty("line", l);
                payload.addProperty("column", c);
            } else {
                payload.addProperty("gameAction", "COME_BACK");
            }
        }

        client.send(new ResponseMsg(requestMsg.getIdentifier(), MessageType.GAME_MESSAGE, payload));
    }

    /**
     * Method used to handle the phase of placing the previously bought development card into one of the development
     * card slots of the board.
     * @param requestMsg the request sent by the server.
     */
    private void handleDevCardPlacement(RequestMsg requestMsg) {
        int x;
        boolean flag;
        Gson gson = new Gson();
        String json = requestMsg.getPayload().get("freeSpots").getAsString();
        Type collectionType = new TypeToken<ArrayList<Integer>>() {
        }.getType();
        ArrayList<Integer> freeSpots = gson.fromJson(json, collectionType);

        System.out.println("\nWhere do you want to want to put the card?");
        fancyPrinter.printDevCardSlot(mySelf.getCompactBoard(),false);
        System.out.println("\nThe available slots are:");
        for (int i = 0; i < freeSpots.size(); i++) {
            freeSpots.set(i, freeSpots.get(i) + 1);
            System.out.println(freeSpots.get(i));
        }
        System.out.println("\nChoose one of them: ");
        x = InputHandler.getIntFromArray(freeSpots);
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "DEVCARD_PLACEMENT");
        payload.addProperty("index", x-1);

        client.send(new ResponseMsg(requestMsg.getIdentifier(), MessageType.GAME_MESSAGE, payload));
    }

    /**
     * Method used to handle the selection of the n powers to activate.
     * @param requestMsg the request sent by the server.
     */
    private void handleChooseProduction(RequestMsg requestMsg){

        ArrayList<Integer> productions = new ArrayList<>();
        ArrayList<Integer> available = new ArrayList<>();

        if(requestMsg.getPayload().has("problem"))
            System.out.println("\nOperation failed, retry or skip action");

        System.out.println("\nPRODUCTION");
        System.out.println("\nThese are your resources");
        fancyPrinter.printWarehouseV2(mySelf.getCompactBoard());
        fancyPrinter.printStrongbox(mySelf.getCompactBoard());
        JsonObject payload = new JsonObject();
        int x=0, y;
        boolean flag;

        System.out.println("\nThese are your productions: ");

        System.out.println("1. Basic production");
        fancyPrinter.printDevCardSlot(mySelf.getCompactBoard(), true);
        available.add(0);
        available.add(1);

        for (int i = 0; i < 3; i++) {
            if(mySelf.getCompactBoard().getDevCardSlots()[i][0] != 0)
            {
                available.add(i+2);
            }

        }

        Resource r1, r2;
        if(mySelf.getCompactBoard().getAbilityActivationFlag()[6] != 0){
            available.add(5);
            r1 = cardCollector.getLeaderCard(mySelf.getCompactBoard().getAbilityActivationFlag()[6]).getSpecialAbility().getAbilityResource();
            System.out.println("5. Special Production with "+r1.toString());
            x = 1;
        }
        if(mySelf.getCompactBoard().getAbilityActivationFlag()[7] != 0){
            available.add(6);
            r2 = cardCollector.getLeaderCard(mySelf.getCompactBoard().getAbilityActivationFlag()[7]).getSpecialAbility().getAbilityResource();
            System.out.println("6. Special Production with "+r2.toString());
            x = 2;
        }

        int n;

        do {
            flag = false;
            System.out.println("\nChoose an action: \n1. Add production\n2. Confirm productions\n3. Go back to main choice");
            n = InputHandler.getInt(1, 3);
            if (n == 1) {
                if(available.size() != 0) {
                    System.out.println("Choose production:  (0 to rollback)");
                    int t = InputHandler.getIntFromArray(available);
                    if(t != 0){
                        productions.add(t);
                        available.removeIf(a -> a == t);
                    }
                }else{
                    System.out.println("You have selected all the available productions");
                }
            } else if (n == 2) {
                if(productions.size() == 0){
                    payload.addProperty("gameAction", "COME_BACK");
                }else {
                    payload.addProperty("gameAction", "CHOOSE_PRODUCTION");
                    Gson gson = new Gson();
                    String json = gson.toJson(productions);
                    payload.addProperty("productions", json);
                    if (productions.contains(1)) {
                        ArrayList<ResourceQty> input = new ArrayList<>();
                        ArrayList<ResourceQty> output = new ArrayList<>();
                        for (int i = 0; i < 3; i++) {
                            if (i == 0 || i == 1)
                                System.out.println("\nChoose an input resource for the basic production: ");
                            else
                                System.out.println("\nChoose an output resource for the basic production: ");
                            System.out.println("1. COIN");
                            System.out.println("2. STONE");
                            System.out.println("3. SHIELD");
                            System.out.println("4. SERVANT");
                            y = InputHandler.getInt(1, 4);
                            if (i == 0 || i == 1)
                                input.add(new ResourceQty(Resource.values()[y - 1], 1));
                            else
                                output.add(new ResourceQty(Resource.values()[y - 1], 1));
                        }
                        Recipe recipe = new Recipe(input, output);
                        String json2 = gson.toJson(recipe);
                        payload.addProperty("basicProduction", json2);
                    }

                    if (productions.contains(5)) {
                        System.out.println("\nChoose an output resource for the special production: ");
                        System.out.println("1. COIN");
                        System.out.println("2. STONE");
                        System.out.println("3. SHIELD");
                        System.out.println("4. SERVANT");
                        y = InputHandler.getInt(1, 4);
                        r1 = Resource.values()[x - 1];
                        String json2 = gson.toJson(r1);
                        payload.addProperty("specialProduction1", json2);
                    }

                    if (productions.contains(6)) {
                        System.out.println("\nChoose an output resource for the special production: ");
                        System.out.println("1. COIN");
                        System.out.println("2. STONE");
                        System.out.println("3. SHIELD");
                        System.out.println("4. SERVANT");
                        y = InputHandler.getInt(1, 4);
                        r2 = Resource.values()[x - 1];
                        String json2 = gson.toJson(r2);
                        payload.addProperty("specialProduction2", json2);
                    }
                }
                flag = true;
            } else {
                payload.addProperty("gameAction", "COME_BACK");
                flag = true;
            }
        }while(!flag);

        client.send(new ResponseMsg(requestMsg.getIdentifier(), MessageType.GAME_MESSAGE, payload));
    }

    /**
     * Method used to handle the selection of the line/column of the market to take resources from.
     * @param requestMsg the request sent by the server.
     */
    private void handlePick(RequestMsg requestMsg){
        JsonObject payload = new JsonObject();
        String s;
        System.out.println("\nMARKET");
        //per qualche motivo lo stampa senza reserve marble
        fancyPrinter.printMarket(compactMarket);


        System.out.println("\nDo you want to pick a line or a column? (l/c) (b-comeback to main choice)");
        s = InputHandler.getString("(l|c|b)");
        if(s.equals("b")){
            payload.addProperty("gameAction", "COME_BACK");
        }else {
            int x;
            if (s.equals("c")) {
                System.out.println("Which column do you want to pick?");
                x = InputHandler.getInt(1, 4);
            } else {
                System.out.println("Which line do you want to pick?");
                x = InputHandler.getInt(1, 3);
            }


            payload.addProperty("gameAction", "PICK");
            payload.addProperty("choice", s);
            payload.addProperty("number", x - 1);

            if ((mySelf.getCompactBoard().getAbilityActivationFlag()[2] != 0) && (mySelf.getCompactBoard().getAbilityActivationFlag()[3] != 0)) {
                System.out.println("\nYou have two special marble that can replace the white one, which one you want: ");
                Resource r1 = cardCollector.getLeaderCard(mySelf.getCompactBoard().getAbilityActivationFlag()[2]).getSpecialAbility().getAbilityResource();
                Resource r2 = cardCollector.getLeaderCard(mySelf.getCompactBoard().getAbilityActivationFlag()[3]).getSpecialAbility().getAbilityResource();
                System.out.println("\n1. " + r1.toString());
                System.out.println("\n2. " + r2.toString());
                x = InputHandler.getInt(1, 2);
                Gson gson = new Gson();
                String json;
                if (x == 1)
                    json = gson.toJson(r1);
                else
                    json = gson.toJson(r2);
                payload.addProperty("resource", json);
            }
        }

        client.send(new ResponseMsg(requestMsg.getIdentifier(), MessageType.GAME_MESSAGE, payload));
    }

    /**
     * Method used to handle the phase of placing the resources received from the market into the warehouse.
     * @param requestMsg the request sent by the server.
     */
    private void handleWarehousePlacement(RequestMsg requestMsg){
        Gson gson = new Gson();
        Type collectionType = new TypeToken<ArrayList<Resource>>(){}.getType();
        String json = requestMsg.getPayload().get("resourcesArray").getAsString();
        ArrayList<Resource> resources = gson.fromJson(json, collectionType);

        if(requestMsg.getPayload().has("problem"))
            System.out.println("\nOperation failed, retry or skip action");

        JsonObject payload = warehousePlacementProcedure(resources);

        client.send(new ResponseMsg(requestMsg.getIdentifier(), MessageType.GAME_MESSAGE, payload));
    }

    /**
     * Method used to handle the updates received from the server.
     * @param requestMsg the request sent by the server.
     */
    private void handleUpdate(RequestMsg requestMsg){

        Gson gson = new Gson();
        JsonObject payload, payload2;
        String message;

        int currentPlayerID = requestMsg.getPayload().get("currentPlayerID").getAsInt();



        CompactPlayer player;
        if(mySelf.getPlayerID() == currentPlayerID)
            player = mySelf;
        else
            player = opponents.get(currentPlayerID); //da controllare



        String json = requestMsg.getPayload().get("discardedLeaderCards").getAsString();
        Type collectionType = new TypeToken<int[]>(){}.getType();
        player.getCompactBoard().removeDiscardedCards(gson.fromJson(json, collectionType));


        payload = requestMsg.getPayload().get("market").getAsJsonObject();
        json = payload.get("structure").getAsString();
        collectionType = new TypeToken<int[]>(){}.getType();
        int[] structure = gson.fromJson(json, collectionType);
        compactMarket.setMarket(structure);

        payload = requestMsg.getPayload().get("devCardStructure").getAsJsonObject();
        json = payload.get("structure").getAsString();
        collectionType = new TypeToken<int[][]>(){}.getType();
        int[][] structure2 = gson.fromJson(json, collectionType);
        compactDevCardStructure.setDevCardStructure(structure2);

        payload = requestMsg.getPayload().get("player").getAsJsonObject();


        json = payload.get("abilityActivationFlag").getAsString();
        collectionType = new TypeToken<int[]>(){}.getType();
        int[] abilityActivationFlag = gson.fromJson(json, collectionType);
        player.getCompactBoard().setAbilityActivationFlag(abilityActivationFlag);

        payload2 = payload.get("warehouse").getAsJsonObject();
        json = payload2.get("structure").getAsString();
        collectionType = new TypeToken<Resource[]>(){}.getType();
        Resource[] ware = gson.fromJson(json, collectionType);
        player.getCompactBoard().setWarehouse(ware);

        payload2 = payload.get("strongbox").getAsJsonObject();
        json = payload2.get("structure").getAsString();
        collectionType = new TypeToken<int[]>(){}.getType();
        player.getCompactBoard().setStrongbox(gson.fromJson(json,collectionType));

        payload2 = payload.get("devCardSlots").getAsJsonObject();
        json = payload2.get("structure").getAsString();
        collectionType = new TypeToken<int[][]>(){}.getType();
        player.getCompactBoard().setDevCardSlots(gson.fromJson(json,collectionType));

        JsonArray players = requestMsg.getPayload().get("faithTracks").getAsJsonArray();
        CompactPlayer p2;
        for (JsonElement p: players) {
            if(mySelf.getPlayerID() == p.getAsJsonObject().get("playerID").getAsInt())
                p2 = mySelf;
            else
                p2 = opponents.get(p.getAsJsonObject().get("playerID").getAsInt());
            payload2 = p.getAsJsonObject().get("faithTrack").getAsJsonObject();
            p2.getCompactBoard().setFaithTrackIndex(payload2.get("index").getAsInt());
            json = payload2.get("favCards").getAsString();
            collectionType = new TypeToken<boolean[]>() {}.getType();
            boolean[] fav = gson.fromJson(json, collectionType);
            p2.getCompactBoard().setFavCards(fav);
        }

        if(requestMsg.getPayload().has("message")){
            message = requestMsg.getPayload().get("message").getAsString();
            if(player.getPlayerID() != mySelf.getPlayerID() || opponents.size()==0)
                System.out.println("\n"+message+"\n");
        }

        if(requestMsg.getPayload().has("lorenzoTrack")){
            JsonObject payload4 = requestMsg.getPayload().get("lorenzoTrack").getAsJsonObject();
            player.getCompactBoard().setLorenzoIndex(payload4.get("index").getAsInt());
        }


        if(requestMsg.getPayload().has("endMessage") && !mySelf.getLastTurn()){
            mySelf.setLastTurn(true);
            System.out.println(requestMsg.getPayload().get("endMessage").getAsString());
        }

        JsonObject payload3 = new JsonObject();
        payload3.addProperty("gameAction", "UPDATE");
        payload3.addProperty("playerID", mySelf.getPlayerID());

        client.send(new ResponseMsg(requestMsg.getIdentifier(), MessageType.GAME_MESSAGE, payload3));
    }

    /**
     * Method that handles the procedure of placing the resources obtained in the market into the player warehouse.
     * @param resourcesToPlace an arrayList containing the resources to be placed.
     * @return a JsonObject containing two properties: an array containing the new configuration of the warehouse and
     * the amount of resources discarded (the ones not placed in the market).
     */
    private JsonObject warehousePlacementProcedure(ArrayList<Resource> resourcesToPlace){
        ArrayList<Resource> resourcesBackup = new ArrayList<>(resourcesToPlace);
        Resource[] warehouseResourcesBackup = mySelf.getCompactBoard().getWarehouse().clone();
        Resource[] warehouseResources = mySelf.getCompactBoard().getWarehouse();
        Resource extraResource1 = Resource.ANY, extraResource2 = Resource.ANY;
        if(mySelf.getCompactBoard().getAbilityActivationFlag()[0] != 0){
            extraResource1 = cardCollector.getLeaderCard(mySelf.getCompactBoard().getAbilityActivationFlag()[0]).getSpecialAbility().getAbilityResource();
            if(mySelf.getCompactBoard().getAbilityActivationFlag()[1] != 0)
                extraResource2 = cardCollector.getLeaderCard(mySelf.getCompactBoard().getAbilityActivationFlag()[1]).getSpecialAbility().getAbilityResource();
        } else if(mySelf.getCompactBoard().getAbilityActivationFlag()[1] != 0){
            extraResource1 = cardCollector.getLeaderCard(mySelf.getCompactBoard().getAbilityActivationFlag()[1]).getSpecialAbility().getAbilityResource();
        }

        int totalSpaces = 6 + (extraResource1 != Resource.ANY ? 2 : 0 ) + (extraResource2 != Resource.ANY ? 2 : 0 );

        boolean exit = false;

        while(!exit){
            fancyPrinter.printWarehouseV2(mySelf.getCompactBoard());
            System.out.println("\nRESOURCES TO PLACE: ");
            for (int i = 0; i < resourcesToPlace.size(); i++) {
                System.out.println((i + 1) + " - " + resourcesToPlace.get(i));
            }
            System.out.println("\nChoose an action:\n1. Add a resource to the warehouse\n2. Move/swap resources in the warehouse" +
                    "\n3. Reset the changes\n4. Exit the placement procedure");
            int choice = InputHandler.getInt(1, 4);
            fancyPrinter.printWarehouseV2(mySelf.getCompactBoard());
            switch (choice){
                case 1: //ADD RESOURCE TO WAREHOUSE
                    if(resourcesToPlace.size() == 0){
                        System.out.println("There are no more resources to place in the warehouse");
                        break;
                    }
                    System.out.println("\nRESOURCES TO PLACE: ");
                    for (int i = 0; i < resourcesToPlace.size(); i++) {
                        System.out.println((i + 1) + " - " + resourcesToPlace.get(i));
                    }
                    System.out.println("\nChoose a resource between the resources still to place (enter 0 to rollback)");
                    int resourceToPlace = InputHandler.getInt(0, resourcesToPlace.size());
                    if(resourceToPlace == 0) break;
                    boolean placed = false;
                    fancyPrinter.printWarehouseV2(mySelf.getCompactBoard());
                    System.out.println("\nCHOSEN RESOURCE: " + resourcesToPlace.get(resourceToPlace - 1));
                    while(!placed){
                        System.out.println("\nSelect a free space in the warehouse to place the resource (enter 0 to rollback).");
                        choice = InputHandler.getInt(0, totalSpaces);
                        if(choice == 0) break;
                        if (warehouseResources[choice - 1] != Resource.ANY) {
                            System.out.println("This spot is already taken! Choose another spot for the resource!");
                        } else {
                            if((choice > 6 && choice < 9 && resourcesToPlace.get(resourceToPlace - 1) != extraResource1) ||
                                    (choice > 8 && resourcesToPlace.get(resourceToPlace - 1) != extraResource2)){
                                System.out.println("The resource can't be placed here!");
                            } else {
                                warehouseResources[choice - 1] = resourcesToPlace.get(resourceToPlace - 1);
                                resourcesToPlace.remove(resourceToPlace - 1);
                                placed = true;
                            }
                        }
                    }
                    break;
                case 2: //MOVE/SWAP RESOURCES
                    System.out.println("\nSelect the resource to move/swap (enter 0 to rollback)");
                    choice = InputHandler.getInt(0, totalSpaces);
                    if(choice == 0) break;
                    boolean moved = false;
                    while(!moved){
                        System.out.println("\nSelect the destination slot (enter 0 to rollback)");
                        int destination = InputHandler.getInt(0, totalSpaces);
                        if(destination == 0) break;
                        if(choice < 7 && destination < 7){
                            Resource res = warehouseResources[choice - 1];
                            warehouseResources[choice - 1] = warehouseResources[destination - 1];
                            warehouseResources[destination - 1] = res;
                            moved = true;
                        } else {
                            System.out.println("Resources in the special warehouses cannot be moved!");
                        }
                    }
                    break;
                case 3: //RESET
                    warehouseResources = warehouseResourcesBackup;
                    resourcesToPlace = new ArrayList<>(resourcesBackup);
                    break;
                case 4: //EXIT
                    if(resourcesToPlace.size() != 0){
                        System.out.println("There are still resources to be placed in the warehouse that will be discarded.\nFor each one of them the other players will receive a faith point.");
                    }
                    System.out.println("Are you sure you want to exit? [yes/no]");
                    String input = InputHandler.getString("(yes|no)");
                    if(input.equals("yes")){
                        exit = true;
                    }
                    break;
            }
        }
        mySelf.getCompactBoard().setWarehouse(warehouseResourcesBackup);
        ArrayList<Resource> newWarehouse = new ArrayList<>(Arrays.asList(warehouseResources));
        Gson gson = new Gson();
        String json = gson.toJson(newWarehouse);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("gameAction", "WAREHOUSE_PLACEMENT");
        jsonObject.addProperty("discarded", resourcesToPlace.size());
        jsonObject.addProperty("placed", json);
        return jsonObject;
    }

    private void handleNotMyTurn(RequestMsg requestMsg){
        System.out.println("\n"+requestMsg.getPayload().get("message").getAsString()+"\n");
        for (HashMap.Entry<Integer, CompactPlayer> entry : opponents.entrySet()) {
            if(entry.getValue().getPlayerID() == requestMsg.getPayload().get("playerID").getAsInt()){
                System.out.println("\n"+entry.getValue().getPlayerName().toUpperCase()+"'S BOARD\n"); //da colorare e mettere in grande
                fancyPrinter.printPersonalBoard(entry.getValue().getCompactBoard());
            }
        }
    }

    private void handleFinalUpdate(RequestMsg requestMsg){
        System.out.println("\nTHE GAME IS ENDED\n");
        JsonArray players = requestMsg.getPayload().get("players").getAsJsonArray();
        int x, vp;
        String name;
        Boolean flag = false;

        if(requestMsg.getPayload().has("finalScenario")){
            switch (requestMsg.getPayload().get("finalScenario").getAsInt()){
                case 1:
                case 2:
                    printFinish(true);
                    System.out.println("Your score is: "+players.get(0).getAsJsonObject().get("victoryPoints").getAsInt());
                    break;
                case 3:
                    printFinish(false);
                    System.out.println("\n Lorenzo has reached the last spot on his Faith Track");
                    break;
                case 4:
                    printFinish(false);
                    System.out.println("\n One column of the Development Card Structure is empty");
                    break;
            }
        }else {
            for (JsonElement player : players) {
                x = player.getAsJsonObject().get("position").getAsInt();
                if (x == 1 && player.getAsJsonObject().get("playerID").getAsInt() == mySelf.getPlayerID()) {
                    printFinish(true);
                    flag = true;
                    break;
                }
            }
            if (!flag)
                printFinish(false);
        }

        if(!requestMsg.getPayload().has("finalScenario")) {
            System.out.println("\nRANKING:");
            for (JsonElement player : players) {
                x = player.getAsJsonObject().get("position").getAsInt();
                vp = player.getAsJsonObject().get("victoryPoints").getAsInt();
                name = player.getAsJsonObject().get("name").getAsString();
                System.out.println(x + ") " + name + ", victory points: " + vp);
            }
        }
    }

    private void printFinish(boolean x){
        if(x){
            System.out.println("\n" +
                    "__/\\\\\\________/\\\\\\_______/\\\\\\\\\\_______/\\\\\\________/\\\\\\____________/\\\\\\______________/\\\\\\_______/\\\\\\\\\\_______/\\\\\\\\\\_____/\\\\\\_        \n" +
                    " _\\///\\\\\\____/\\\\\\/______/\\\\\\///\\\\\\____\\/\\\\\\_______\\/\\\\\\___________\\/\\\\\\_____________\\/\\\\\\_____/\\\\\\///\\\\\\____\\/\\\\\\\\\\\\___\\/\\\\\\_       \n" +
                    "  ___\\///\\\\\\/\\\\\\/______/\\\\\\/__\\///\\\\\\__\\/\\\\\\_______\\/\\\\\\___________\\/\\\\\\_____________\\/\\\\\\___/\\\\\\/__\\///\\\\\\__\\/\\\\\\/\\\\\\__\\/\\\\\\_      \n" +
                    "   _____\\///\\\\\\/_______/\\\\\\______\\//\\\\\\_\\/\\\\\\_______\\/\\\\\\___________\\//\\\\\\____/\\\\\\____/\\\\\\___/\\\\\\______\\//\\\\\\_\\/\\\\\\//\\\\\\_\\/\\\\\\_     \n" +
                    "    _______\\/\\\\\\_______\\/\\\\\\_______\\/\\\\\\_\\/\\\\\\_______\\/\\\\\\____________\\//\\\\\\__/\\\\\\\\\\__/\\\\\\___\\/\\\\\\_______\\/\\\\\\_\\/\\\\\\\\//\\\\\\\\/\\\\\\_    \n" +
                    "     _______\\/\\\\\\_______\\//\\\\\\______/\\\\\\__\\/\\\\\\_______\\/\\\\\\_____________\\//\\\\\\/\\\\\\/\\\\\\/\\\\\\____\\//\\\\\\______/\\\\\\__\\/\\\\\\_\\//\\\\\\/\\\\\\_   \n" +
                    "      _______\\/\\\\\\________\\///\\\\\\__/\\\\\\____\\//\\\\\\______/\\\\\\_______________\\//\\\\\\\\\\\\//\\\\\\\\\\______\\///\\\\\\__/\\\\\\____\\/\\\\\\__\\//\\\\\\\\\\\\_  \n" +
                    "       _______\\/\\\\\\__________\\///\\\\\\\\\\/______\\///\\\\\\\\\\\\\\\\\\/_________________\\//\\\\\\__\\//\\\\\\_________\\///\\\\\\\\\\/_____\\/\\\\\\___\\//\\\\\\\\\\_ \n" +
                    "        _______\\///_____________\\/////__________\\/////////____________________\\///____\\///____________\\/////_______\\///_____\\/////__\n");
        }
        else{
            System.out.println("\n" +
                    "__/\\\\\\________/\\\\\\_______/\\\\\\\\\\_______/\\\\\\________/\\\\\\____________/\\\\\\___________________/\\\\\\\\\\__________/\\\\\\\\\\\\\\\\\\\\\\____/\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\_        \n" +
                    " _\\///\\\\\\____/\\\\\\/______/\\\\\\///\\\\\\____\\/\\\\\\_______\\/\\\\\\___________\\/\\\\\\_________________/\\\\\\///\\\\\\______/\\\\\\/////////\\\\\\_\\///////\\\\\\/////__       \n" +
                    "  ___\\///\\\\\\/\\\\\\/______/\\\\\\/__\\///\\\\\\__\\/\\\\\\_______\\/\\\\\\___________\\/\\\\\\_______________/\\\\\\/__\\///\\\\\\___\\//\\\\\\______\\///________\\/\\\\\\_______      \n" +
                    "   _____\\///\\\\\\/_______/\\\\\\______\\//\\\\\\_\\/\\\\\\_______\\/\\\\\\___________\\/\\\\\\______________/\\\\\\______\\//\\\\\\___\\////\\\\\\_______________\\/\\\\\\_______     \n" +
                    "    _______\\/\\\\\\_______\\/\\\\\\_______\\/\\\\\\_\\/\\\\\\_______\\/\\\\\\___________\\/\\\\\\_____________\\/\\\\\\_______\\/\\\\\\______\\////\\\\\\____________\\/\\\\\\_______    \n" +
                    "     _______\\/\\\\\\_______\\//\\\\\\______/\\\\\\__\\/\\\\\\_______\\/\\\\\\___________\\/\\\\\\_____________\\//\\\\\\______/\\\\\\__________\\////\\\\\\_________\\/\\\\\\_______   \n" +
                    "      _______\\/\\\\\\________\\///\\\\\\__/\\\\\\____\\//\\\\\\______/\\\\\\____________\\/\\\\\\______________\\///\\\\\\__/\\\\\\_____/\\\\\\______\\//\\\\\\________\\/\\\\\\_______  \n" +
                    "       _______\\/\\\\\\__________\\///\\\\\\\\\\/______\\///\\\\\\\\\\\\\\\\\\/_____________\\/\\\\\\\\\\\\\\\\\\\\\\\\\\\\\\____\\///\\\\\\\\\\/_____\\///\\\\\\\\\\\\\\\\\\\\\\/_________\\/\\\\\\_______ \n" +
                    "        _______\\///_____________\\/////__________\\/////////_______________\\///////////////_______\\/////_________\\///////////___________\\///________\n");
        }
    }

    private void handleReconnectionUpdate(RequestMsg requestMsg){
        compactMarket = new CompactMarket();
        compactDevCardStructure = new CompactDevCardStructure();

        int playerID = requestMsg.getPayload().get("myPlayerID").getAsInt();
        String playerName = requestMsg.getPayload().get("myName").getAsString();
        mySelf = new CompactPlayer(playerID, playerName);

        JsonObject payload = requestMsg.getPayload().get("market").getAsJsonObject();
        Gson gson = new Gson();
        String json = payload.get("structure").getAsString();
        Type collectionType = new TypeToken<int[]>(){}.getType();
        int[] structure = gson.fromJson(json, collectionType);
        compactMarket.setMarket(structure);

        payload = requestMsg.getPayload().get("devCardStructure").getAsJsonObject();
        json = payload.get("structure").getAsString();
        collectionType = new TypeToken<int[][]>(){}.getType();
        int[][] structure2 = gson.fromJson(json, collectionType);
        compactDevCardStructure.setDevCardStructure(structure2);

        JsonObject payload2;
        JsonArray players = requestMsg.getPayload().get("players").getAsJsonArray();
        for (JsonElement player: players) {
            if(player.getAsJsonObject().get("playerID").getAsInt() == mySelf.getPlayerID()){
                payload2 = player.getAsJsonObject().get("faithTrack").getAsJsonObject();
                mySelf.getCompactBoard().setFaithTrackIndex(payload2.get("index").getAsInt());
                json = payload2.get("favCards").getAsString();
                collectionType = new TypeToken<boolean[]>(){}.getType();
                boolean[] fav = gson.fromJson(json, collectionType);
                mySelf.getCompactBoard().setFavCards(fav);

                json = player.getAsJsonObject().get("abilityActivationFlag").getAsString();
                collectionType = new TypeToken<int[]>(){}.getType();
                int[] abilityActivationFlag = gson.fromJson(json, collectionType);
                mySelf.getCompactBoard().setAbilityActivationFlag(abilityActivationFlag);

                payload2 = player.getAsJsonObject().get("warehouse").getAsJsonObject();
                json = payload2.get("structure").getAsString();
                collectionType = new TypeToken<Resource[]>(){}.getType();
                Resource[] ware = gson.fromJson(json, collectionType);
                mySelf.getCompactBoard().setWarehouse(ware);

                payload2 = player.getAsJsonObject().get("strongbox").getAsJsonObject();
                json = payload2.get("structure").getAsString();
                collectionType = new TypeToken<int[]>(){}.getType();
                mySelf.getCompactBoard().setStrongbox(gson.fromJson(json,collectionType));

                json = player.getAsJsonObject().get("leaderCards").getAsString();
                collectionType = new TypeToken<int[]>(){}.getType();
                int[] lcs = gson.fromJson(json, collectionType);
                mySelf.getCompactBoard().setLeaderCards(lcs);

            }else{
                opponents.put(player.getAsJsonObject().get("playerID").getAsInt(), new CompactPlayer(player.getAsJsonObject().get("playerID").getAsInt(),player.getAsJsonObject().get("playerName").getAsString()));

                opponents.get(player.getAsJsonObject().get("playerID").getAsInt()).getCompactBoard().setOpponent(true);

                json = player.getAsJsonObject().get("abilityActivationFlag").getAsString();
                collectionType = new TypeToken<int[]>(){}.getType();
                int[] abilityActivationFlag = gson.fromJson(json, collectionType);
                opponents.get(player.getAsJsonObject().get("playerID").getAsInt()).getCompactBoard().setAbilityActivationFlag(abilityActivationFlag);

                payload2 = player.getAsJsonObject().get("faithTrack").getAsJsonObject();
                opponents.get(player.getAsJsonObject().get("playerID").getAsInt()).getCompactBoard().setFaithTrackIndex(payload2.get("index").getAsInt());
                json = payload2.get("favCards").getAsString();
                collectionType = new TypeToken<boolean[]>(){}.getType();
                boolean[] fav = gson.fromJson(json, collectionType);
                opponents.get(player.getAsJsonObject().get("playerID").getAsInt()).getCompactBoard().setFavCards(fav);

                payload2 = player.getAsJsonObject().get("warehouse").getAsJsonObject();
                json = payload2.get("structure").getAsString();
                collectionType = new TypeToken<ArrayList<Resource>>(){}.getType();
                ArrayList<Resource> ware = gson.fromJson(json, collectionType);
                Resource[] wa = new Resource[10];
                wa = ware.toArray(wa);
                opponents.get(player.getAsJsonObject().get("playerID").getAsInt()).getCompactBoard().setWarehouse(wa);

                payload2 = player.getAsJsonObject().get("strongbox").getAsJsonObject();
                json = payload2.get("structure").getAsString();
                collectionType = new TypeToken<int[]>(){}.getType();
                opponents.get(player.getAsJsonObject().get("playerID").getAsInt()).getCompactBoard().setStrongbox(gson.fromJson(json,collectionType));


                json = player.getAsJsonObject().get("leaderCards").getAsString();
                collectionType = new TypeToken<int[]>(){}.getType();
                int[] lcs = gson.fromJson(json, collectionType);
                opponents.get(player.getAsJsonObject().get("playerID").getAsInt()).getCompactBoard().setLeaderCards(lcs);
            }
        }

        String currPlayer = requestMsg.getPayload().get("currPlayer").getAsString();

        System.out.println("\nYou are back in the game");
        System.out.println("\n"+currPlayer+" is now playing");

        for (HashMap.Entry<Integer, CompactPlayer> entry : opponents.entrySet()) {
            if(entry.getValue().getPlayerName().equals(currPlayer)){
                System.out.println("\n"+entry.getValue().getPlayerName().toUpperCase()+"'S BOARD\n"); //da colorare e mettere in grande
                fancyPrinter.printPersonalBoard(entry.getValue().getCompactBoard());
            }
        }

    }


}
