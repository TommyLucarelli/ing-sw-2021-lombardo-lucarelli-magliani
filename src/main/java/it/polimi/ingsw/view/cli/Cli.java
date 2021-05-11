package it.polimi.ingsw.view.cli;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.core.model.LeaderCard;
import it.polimi.ingsw.core.model.Resource;
import it.polimi.ingsw.net.client.Client;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.UserInterface;
import it.polimi.ingsw.view.compact.CardCollector;
import it.polimi.ingsw.view.compact.CompactDevCardStructure;
import it.polimi.ingsw.view.compact.CompactMarket;
import it.polimi.ingsw.view.compact.CompactPlayer;

import java.io.FileNotFoundException;
import java.lang.reflect.Type;
import java.util.*;

/**
 * Main class for CLI operations.
 */
public class Cli implements UserInterface {
    Client client;
    CompactPlayer mySelf;
    CardCollector cardCollector;
    FancyPrinter fancyPrinter;
    CompactMarket compactMarket;
    CompactDevCardStructure compactDevCardStructure;
    HashMap<String, CompactPlayer> opponents;
    Scanner scan = new Scanner(System.in);

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
                    case "LEADER_ACTION":
                        handleLeaderAction(request);
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
        Type collectionType = new TypeToken<int[]>(){}.getType();
        int[] leaderCards = gson.fromJson(json, collectionType);

        System.out.println("\nThe game has started!!");
        System.out.println("You are player "+requestMsg.getPayload().get("playerOrder").getAsInt());

        for (int i = 0; i < 4; i++) {
            lc = cardCollector.getLeaderCard(leaderCards[i]);
            System.out.println("."+(i+1));
            //fancyPrinter.printLeaderCard(lc);
        }
        //TODO: controllo
        do {
            System.out.println("\nChoose a card to discard");
            x = scan.nextInt();
        }while(x>4 || x<1);
        do {
            System.out.println("\nChoose a second card to discard");
            y = scan.nextInt();
        }while(y>4 || y<1 || x==y);

        for (int i = 0; i < 4; i++) {
            if (i == (x - 1) || i == (y - 1)) {
                discardedLeaders[j] = leaderCards[i];
                j++;
            } else {
                leaders[k] = leaderCards[i];
                k++;
            }
        }

        mySelf.getCompactBoard().setLeaderCards(leaders);

        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "CHOOSE_START_LEADERS");
        payload.addProperty("playerID", mySelf.getPlayerID());
        json = gson.toJson(discardedLeaders); //forse sarebbe meglio trasformarlo in array
        payload.addProperty("discardedLeaders", json);

        client.send(new ResponseMsg(requestMsg.getIdentifier(), MessageType.GAME_MESSAGE, payload));
    }

    private void handleChooseStartResources(RequestMsg requestMsg){
        int x, y, n;
        Resource[] placed1 = new Resource[10];

        Resource a, b;
        if(requestMsg.getPayload().get("resources").getAsInt() == 0)
            System.out.println("\nWait for the other players to finish their initial turn");
        else{
            x = requestMsg.getPayload().get("resources").getAsInt();
            y = requestMsg.getPayload().get("faithPoints").getAsInt();
            System.out.println("\nYou are entitled to "+x+" resources ");
            if(y!=0)
                System.out.println("and "+y+" faith points");

            System.out.println("\nChoose "+x+" resources:");
            System.out.println("\n1. COIN");
            System.out.println("\n2. SHIELD");
            System.out.println("\n3. STONE");
            System.out.println("\n4. SERVANT");

            //TODO: controllo
            n = scan.nextInt();
            a = Resource.values()[n-1];
            if(x == 2){
                n = scan.nextInt();
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

    private void handleInitialUpdate(RequestMsg ms){
        compactMarket = new CompactMarket();
        compactDevCardStructure = new CompactDevCardStructure();

        JsonObject payload = ms.getPayload().get("market").getAsJsonObject();
        Gson gson = new Gson();
        String json = payload.get("structure").getAsString();
        Type collectionType = new TypeToken<int[]>(){}.getType();
        int[] structure = gson.fromJson(json, collectionType);
        compactMarket.setMarket(structure);
        compactMarket.setReserveMarble(payload.get("reserveMarble").getAsInt());

        payload = ms.getPayload().get("devCardStructure").getAsJsonObject();
        json = payload.get("structure").getAsString();
        collectionType = new TypeToken<int[][]>(){}.getType();
        int[][] structure2 = gson.fromJson(json, collectionType);
        compactDevCardStructure.setDevCardStructure(structure2);

        JsonObject payload2;
        for (int i = 0; i < ms.getPayload().get("numOfPlayers").getAsInt(); i++) {
            payload = ms.getPayload().get("player"+i).getAsJsonObject();
            if(payload.get("playerID").getAsInt() == mySelf.getPlayerID()){
                payload2 = payload.get("faithTrack").getAsJsonObject();
                mySelf.getCompactBoard().setFaithTrackIndex(payload2.get("index").getAsInt());
                json = payload2.get("favCards").getAsString();
                collectionType = new TypeToken<boolean[]>(){}.getType();
                boolean[] fav = gson.fromJson(json, collectionType);
                mySelf.getCompactBoard().setFavCards(fav);

                payload2 = payload.get("warehouse").getAsJsonObject();
                json = payload2.get("structure").getAsString();
                collectionType = new TypeToken<ArrayList<Resource>>(){}.getType();
                ArrayList<Resource> ware = gson.fromJson(json, collectionType);
                Resource[] wa = new Resource[10];
                wa = ware.toArray(wa);
                mySelf.getCompactBoard().setWarehouse(wa);

            }else{
                opponents.put(payload.get("playerName").getAsString(), new CompactPlayer(payload.get("playerID").getAsInt(),payload.get("playerName").getAsString()));

                payload2 = payload.get("faithTrack").getAsJsonObject();
                opponents.get(payload.get("playerName").getAsString()).getCompactBoard().setFaithTrackIndex(payload2.get("index").getAsInt());
                json = payload2.get("favCards").getAsString();
                collectionType = new TypeToken<boolean[]>(){}.getType();
                boolean[] fav = gson.fromJson(json, collectionType);
                opponents.get(payload.get("playerName").getAsString()).getCompactBoard().setFavCards(fav);

                payload2 = payload.get("warehouse").getAsJsonObject();
                json = payload2.get("structure").getAsString();
                collectionType = new TypeToken<ArrayList<Resource>>(){}.getType();
                ArrayList<Resource> ware = gson.fromJson(json, collectionType);
                Resource[] wa = new Resource[10];
                wa = ware.toArray(wa);
                mySelf.getCompactBoard().setWarehouse(wa);
            }
        }

        JsonObject payload3 = new JsonObject();
        payload3.addProperty("gameAction", "INITIAL_UPDATE");

        client.send(new ResponseMsg(UUID.randomUUID(), MessageType.GAME_MESSAGE, payload3));
    }

    private void handleLeaderActivation(RequestMsg requestMsg){
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "LEADER_ACTIVATION");
        boolean flag;
        do {
            flag = false;
            System.out.println("\nDo you want to activate or discard a Leader Card? [yes/no]");
            String answer = scan.nextLine();
            if(answer.equals("yes"))
                payload.addProperty("activation", true);
            else if(answer.equals("false"))
                payload.addProperty("activation", false);
            else
                flag = true;
        }while (flag);

        client.send(new ResponseMsg(requestMsg.getIdentifier(), MessageType.GAME_MESSAGE, payload));
    }

    private void handleLeaderAction(RequestMsg requestMsg){
        JsonObject payload = new JsonObject();
        int x;

        do{
            System.out.println("\nChoose a Leader Card to activate or discard: ");
            System.out.println("\n1.");
            //fancyPrinter.printLeaderCard(mySelf.getCompactBoard().getLeaderCards()[0]);
            System.out.println("\n2.");
            //fancyPrinter.printLeaderCard(mySelf.getCompactBoard().getLeaderCards()[1]);
            System.out.println("\n3. to move on to the main action of the turn");
            x = scan.nextInt();
            if(x == 1){
                payload.addProperty("gameAction", "LEADER_ACTION");
                payload.addProperty("cardID", mySelf.getCompactBoard().getLeaderCards()[0]);
            }else if(x == 2){
                payload.addProperty("gameAction", "LEADER_ACTION");
                payload.addProperty("cardID", mySelf.getCompactBoard().getLeaderCards()[0]);
            }else if(x == 3)
                payload.addProperty("gameAction", "COME_BACK");
        }while (x < 1 || x > 3);

        if(x == 1 || x == 2){
            do{
                System.out.println("\nWhich action do you want to perform:");
                System.out.println("1. Activate");
                System.out.println("2. Discard");
                x = scan.nextInt();
                if(x == 1)
                    payload.addProperty("action", true);
                else if(x == 2)
                    payload.addProperty("action", false);
            }while(x < 1 || x > 2);
        }

        client.send(new ResponseMsg(requestMsg.getIdentifier(), MessageType.GAME_MESSAGE, payload));

    }


}
