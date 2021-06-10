package it.polimi.ingsw.view.gui.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.core.model.Resource;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.gui.JavaFxApp;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class WhiteMarbleController implements DynamicController{
    @FXML
    AnchorPane pane1, pane2, pane3, pane4;

    @FXML
    ImageView resource1, resource2, resource3, resource4;

    JsonObject payload;
    int[] specialResources = new int[2];
    int[] marbleSwitch;
    @Override
    public void setData(JsonObject data) {
        int whiteMarbles = data.get("whiteMarbles").getAsInt();
        marbleSwitch = new int[whiteMarbles];

        for (int i = 0; i < 2; i++) {
            switch (JavaFxApp.getManager().getMyself().getCompactBoard().getAbilityActivationFlag()[2 + i]){
                case 57: //SERVANT
                    specialResources[i] = 3;
                    break;
                case 58: //SHIELD
                    specialResources[i] = 2;
                    break;
                case 59: //STONE
                    specialResources[i] = 1;
                    break;
                case 60: //COIN
                    specialResources[i] = 0;
                    break;
            }
        }

        this.payload = data;

        ArrayList<AnchorPane> panes = new ArrayList<>(Arrays.asList(pane1, pane2, pane3, pane4));
        ArrayList<ImageView> images = new ArrayList<>(Arrays.asList(resource1, resource2, resource3, resource4));

        for (AnchorPane pane: panes) {
            pane.setVisible(false);
        }

        for (int i = 0; i < whiteMarbles; i++) {
            panes.get(i).setVisible(true);
            int finalI = i;
            images.get(i).setOnMouseClicked(evt -> {
                marbleSwitch[finalI] = (marbleSwitch[finalI] + 1) % 2;
                images.get(finalI).setImage(new Image(getClass().getResourceAsStream("/images/resources/" + specialResources[marbleSwitch[finalI]] + ".png")));
            });
        }
    }

    public void confirmAction(){
        Gson gson = new Gson();
        for (int i = 0; i < payload.get("whiteMarbles").getAsInt(); i++) {
            payload.addProperty("resource" + i, gson.toJson(Resource.values()[specialResources[marbleSwitch[i]]]));
        }

        payload.remove("whiteMarbles");

        JavaFxApp.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload));
        Stage stage = (Stage) resource1.getScene().getWindow();
        stage.close();
    }
}
