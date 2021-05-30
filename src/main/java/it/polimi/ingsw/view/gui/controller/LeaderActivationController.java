package it.polimi.ingsw.view.gui.controller;

import com.google.gson.JsonObject;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.gui.JavaFxApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller class for the leader activation scene.
 */
public class LeaderActivationController {
    @FXML
    Button yesBtn, noBtn;

    /**
     * onAction method: calls activate() with activation = false;
     */
    public void noAction(){
        activate(noBtn, false);
    }

    /**
     * onAction method: calls activate() with activation = true;
     */
    public void yesAction(){
        activate(yesBtn, true);
    }

    /**
     * Sends to the server the selected action
     * @param source the pressed button (used to close the popup)
     * @param activation true if the player wants to activate/discard a leader card, false otherwise.
     */
    public void activate(Button source, boolean activation){
        JsonObject payload = new JsonObject();
        payload.addProperty("activation", activation);
        payload.addProperty("gameAction", "LEADER_ACTIVATION");

        JavaFxApp.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload));

        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
