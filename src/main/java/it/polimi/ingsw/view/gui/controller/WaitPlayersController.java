package it.polimi.ingsw.view.gui.controller;

import com.google.gson.JsonObject;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.gui.JavaFxApp;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

/**
 * Controller class for the wait players scene.
 */
public class WaitPlayersController implements DynamicController {
    StringBuilder text;

    @FXML
    Text textArea;

    @FXML
    Button startGameBtn;

    @Override
    public void setData(JsonObject data) {
        text = new StringBuilder(textArea.getText());
        switch (data.get("gameAction").getAsString()){
            case "WAIT_FOR_PLAYERS":
            case "WAIT_START_GAME":
                textArea.setText(data.get("message").getAsString());
                text = new StringBuilder(textArea.getText());
                break;
            case "SHORT_UPDATE":
                text.append("\n").append(data.get("message").getAsString());
                textArea.setText(text.toString());
                break;
            case "START_GAME_COMMAND":
                text.append("\n").append("All players have joined the lobby!");
                textArea.setText(text.toString());
                startGameBtn.setVisible(true);
                break;
        }
    }

    /**
     * onAction method: sends to the server the command to start the game.
     */
    public void startGameAction(){
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "START_GAME_COMMAND");
        payload.addProperty("input", "start");
        JavaFxApp.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload));
    }
}
