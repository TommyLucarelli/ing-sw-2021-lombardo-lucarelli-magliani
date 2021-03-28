package it.polimi.ingsw.core;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Class representing the main game
 * @author Giacomo Lombardo
 */
public class Game
{
    private int gameId;
    private ArrayList<Player> players;
    private Market market;
    private DevCardStructure devCardStructure;
    private LeaderCardsDeck leaderCards;

    /**
     * Class constructor.
     * @param id the id of the game
     * @param playerNames an ArrayList containing the names of the players which will be generated
     */
    public Game(int id, ArrayList<String> playerNames){
        this.gameId = id;
        for(int i = 0; i < playerNames.size(); i++){
            players.add(new Player(i, playerNames.get(i)));
        }
        this.market = new Market();
        this.devCardStructure = new DevCardStructure();
        this.leaderCards = new LeaderCardsDeck();
    }

    public static void main( String[] args )
    {
        Gson g = new Gson();
        ArrayList<ResourceQty> startRes = new ArrayList<ResourceQty>();
        ArrayList<ResourceQty> endRes = new ArrayList<ResourceQty>();
        ArrayList<ResourceQty> cost = new ArrayList<ResourceQty>();
        startRes.add(new ResourceQty(Resource.COIN, 1));
        endRes.add(new ResourceQty(Resource.FAITH, 1));
        Recipe rec = new Recipe(startRes, endRes);
        cost.add(new ResourceQty(Resource.SHIELD, 2));
        DevCard card = new DevCard(1, new Flag(1, Colour.GREEN), rec, cost, 1);
        System.out.println(g.toJson(card));

        String json = "{\n" +
                "    \"id\":1,\n" +
                "    \"flag\":{\n" +
                "        \"level\":1,\n" +
                "        \"colour\":\"GREEN\"\n" +
                "    },\n" +
                "    \"recipe\":\n" +
                "    {\n" +
                "        \"inputResources\":\n" +
                "        [\n" +
                "            {\n" +
                "                \"resource\":\"COIN\",\n" +
                "                \"qty\":1\n" +
                "            }\n" +
                "        ],\n" +
                "        \"outputResources\":\n" +
                "        [\n" +
                "            {\n" +
                "                \"resource\":\"FAITH\",\n" +
                "                \"qty\":1\n" +
                "            }\n" +
                "        ]\n" +
                "    },\n" +
                "    \"cost\":\n" +
                "    [\n" +
                "        {\n" +
                "            \"resource\":\"SHIELD\",\n" +
                "            \"qty\":2\n" +
                "        }\n" +
                "    ],\n" +
                "    \"victoryPoints\":1\n" +
                "}";
        DevCard card1 = g.fromJson(json, DevCard.class);
        System.out.println(card1);
        System.out.println(card1.getRecipe().getInputResources().get(0).getResource());
    }
}
