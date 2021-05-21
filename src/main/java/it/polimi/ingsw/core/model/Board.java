package it.polimi.ingsw.core.model;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.util.ArrayList;

/**
 * Class for the representation of the board
 * @author Tommaso Lucarelli
 */
public class Board {
    private Warehouse warehouse;
    private Strongbox strongbox;
    private FaithTrack faithtrack;
    private ArrayList<DevCardSlot> devCardSlots;
    private ArrayList<LeaderCard> leaderCards;
    private Player player;

    /**
     * 0 if the ability isn't activated, a number representing the id of the card is the ability is activated
     * abiltyActivationFlag layout:
     * 0 - special warehouse 1
     * 1 - special warehouse 2
     * 2 - special marble 1
     * 3 - special marble 2
     * 4 - special discount 1
     * 5 - special discount 2
     * 6 - special production 1
     * 7 - special production 2
     */
    private int[] abilityActivationFlag;


    /**
     * Constructor method that initialize each structure inside the board
     */
    public Board()
    {
        warehouse = new Warehouse();
        strongbox = new Strongbox();
        faithtrack = new FaithTrack();
        devCardSlots = new ArrayList<DevCardSlot>();
        devCardSlots.add(new DevCardSlot());
        devCardSlots.add(new DevCardSlot());
        devCardSlots.add(new DevCardSlot());
        leaderCards = new ArrayList<LeaderCard>();
        abilityActivationFlag = new int[8]; //dovrebbero essere già tutti 0
    }

    /**
     * Getter method
     * @return the faith track
     */
    public FaithTrack getFaithTrack() {
        return faithtrack;
    }

    /**
     * Getter method
     * @return the strongbox
     */
    public Strongbox getStrongbox() {
        return strongbox;
    }

    /**
     * Getter method
     * @return the warehouse
     */
    public Warehouse getWarehouse() {
        return warehouse;
    }

    /**
     * Getter method
     * @return the slot for Development Card selected
     */
    public DevCardSlot getDevCardSlot(int i){
        return devCardSlots.get(i);
    }

    /**
     * Getter method
     * @return the Leader Card selected
     */
    public LeaderCard getLeaderCard(int i){
        return leaderCards.get(i);
    }

    /**
     * Method to give the leader card to each player at the beginning of the game
     * @param lc is the Leader Card that is being inserted in the array
     */
    public void addLeaderCard(LeaderCard lc){
        leaderCards.add(lc);
    }

    /**
     * Removes a leader card from the player's board.
     * @param lc the leader card to be removed.
     */
    public void removeLeaderCard(LeaderCard lc){
        leaderCards.remove(lc);
    }


    /**
     * Setter method to set true the flag of a special ability that has become active
     * @param i spot representing the ability that the user has activated, the spot layout is determined by the legend above
     * @param id the identifier of the card with the special ability
     */
    public void setAbilityActivationFlag(int i, int id) {
        abilityActivationFlag[(abilityActivationFlag[i * 2] == 0) ? (i * 2) : (i * 2) + 1] = id;
    }

    /**
     * Getter method.
     * @return returns an array containing the information about the special abilities activated and their leader card.
     */
    public int[] getAbilityActivationFlag(){
        return abilityActivationFlag.clone();
    }

    /**
     * getter method to to see whether an ability is activated or not
     * @param i spot representing the ability that the user has activated, the spot layout is determined by the legend above
     * @return the current value of the flag
     */
    public int isActivated(int i){
        return abilityActivationFlag[i];
    }

    /**
     * Getter method
     * @param id of the LeaderCard
     * @return the LeaderCard
     */
    public LeaderCard getLeader(int id){
        for (LeaderCard lc : leaderCards) {
            if(lc.getId() == id)
                return lc;
        }
        /* se non avviene nessuno problema, ci può stare fare un exception */
        return null;
    }

    /**
     * @return the player resources with the following layout: COIN|STONE|SHIELD|SERVANT
     */
    public int[] personalResQtyToArray() {
        int[] resPlayer = {0,0,0,0};

        for (int i = 0; i < warehouse.getStructure().size(); i++) {
            if (!warehouse.getStructure().get(i).equals(Resource.ANY)) {
                resPlayer[warehouse.getStructure().get(i).ordinal()]++;
            }
        }
        for (int i = 0; i < strongbox.getResources().size(); i++) {
                resPlayer[strongbox.getResources().get(i).getResource().ordinal()] += strongbox.getResources().get(i).getQty();
        }
        return resPlayer;
    }

    /**
     * @param f
     * @param level
     * @return number of required flags to buy a leaderCard
     */
    public int countFlags(Flag f, boolean level) {
        int count = 0;
        ArrayList<Flag> flags;
        if(level){
            for(int i=0; i<devCardSlots.size(); i++){
                flags = devCardSlots.get(i).getSlotFlags();
                if(flags.size() >= f.getLevel() && flags.get(f.getLevel() - 1).getColour() == f.getColour())
                    count++;
            }
        }else{
            for(int i=0; i<devCardSlots.size(); i++){
                flags = devCardSlots.get(i).getSlotFlags();
                for(int j=0; j<flags.size(); j++){
                    if(flags.get(j).getColour() == f.getColour())
                        count++;
                }
            }
        }
        return count;
    }

    /**
     * @return the number of total victoryPoints acquired in the game
     */
    public int victoryPoints(){
        int vp=0;
        int cont=0;
        int x[], dim;
        for(int i=0; i<3; i++){
            dim = devCardSlots.get(i).getSlot().size();
            for (int j = 0; j < dim; j++) {
                vp+=devCardSlots.get(i).getSlot().get(j).getVictoryPoints();
            }
        }
        vp+= faithtrack.getPosition();
        vp+= faithtrack.favourVictoryPoints();
        x = personalResQtyToArray();
        for (int i : x) {
            cont+=i;
        }
        if(leaderCards.get(0).getAbilityActivation())
            vp+=leaderCards.get(0).getVictoryPoints();
        if(leaderCards.get(1).getAbilityActivation())
            vp+=leaderCards.get(1).getVictoryPoints();
        vp+= (cont/5);
        return vp;
    }

    /**
     * @return the number of DecCard in the player devCardSlot
     */
    public int numberOfDevCard(){
        int cont=0;
        for (DevCardSlot d : devCardSlots) {
            cont+=d.numberOfDevCard();
        }
        return cont;
    }

    /**
     * Getter method used for network communication.
     * @return an object containing the information about the board's development card slots.
     */
    public JsonObject toCompactDevCardSlots(){
        int[][] mat = new int[3][3]; //sono tutti 0 giusto??
        ArrayList<DevCard> arr;

        for(int i=0; i<devCardSlots.size(); i++) {
            arr = devCardSlots.get(i).getSlot();
            for (int j = 0; j < arr.size(); j++) {
                mat[i][j] = arr.get(j).getId(); //da capire se la disposizione è giusta
            }
        }

        Gson gson = new Gson();
        JsonObject payload = new JsonObject();
        String json = gson.toJson(mat);
        payload.addProperty("structure", json);

        return payload;
    }


}
