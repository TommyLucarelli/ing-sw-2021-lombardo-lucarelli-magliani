package it.polimi.ingsw.view.gui.controller;

import com.google.gson.JsonObject;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.gui.JavaFxApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.stage.Stage;

/**
 * Controller class for the main choice popup scene.
 */
public class MainChoiceController {
    @FXML
    Button prodBtn, marketBtn, devBtn;

    /**
     * onAction method: calls action() with action = "production".
     */
    public void prodAction(){
        action(prodBtn, "production");
    }

    /**
     * onAction method: calls action() with action = "market".
     */
    public void marketAction(){
        action(marketBtn, "market");
    }

    /**
     * onAction method: calls action() with action = "production".
     */
    public void devAction(){
        action(devBtn, "buyDevCard");
    }

    /**
     * Sends to the server the selected action, then closes the popup.
     * @param source the pressed button.
     * @param action the action relative to the button.
     */
    public void action(Button source, String action){
        JsonObject payload = new JsonObject();
        payload.addProperty("actionChoice", action);
        payload.addProperty("gameAction", "MAIN_CHOICE");

        JavaFxApp.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload));

        Stage stage = (Stage) source.getScene().getWindow();
        stage.close();
    }
}
