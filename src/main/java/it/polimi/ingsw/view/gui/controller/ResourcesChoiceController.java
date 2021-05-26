package it.polimi.ingsw.view.gui.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import it.polimi.ingsw.core.model.Resource;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.gui.JavaFxApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class ResourcesChoiceController implements DynamicController, Initializable {
    JsonObject data;
    int resources;
    int[] selected = new int[4];

    @FXML
    Text text;

    @FXML
    TextField coin, shield, stone, servant;

    @FXML
    Button confirmBtn, incCoinBtn, decCoinBtn, incStoneBtn, decStoneBtn, incShieldBtn, decShieldBtn, incServantBtn, decServantBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        confirmBtn.setDisable(true);
        decCoinBtn.setDisable(true);
        decStoneBtn.setDisable(true);
        decServantBtn.setDisable(true);
        decShieldBtn.setDisable(true);
    }

    @Override
    public void setData(JsonObject data) {
        this.data = data;
        this.resources = data.get("resources").getAsInt();
        if(resources == 0){
            JsonObject payload = new JsonObject();
            payload.addProperty("message", "Waiting for other players...");
            JavaFxApp.setRootWithData("loading", payload);
        }
        if(data.has("faithPoints")) text.setText("You are entitled to " + resources + " starting resources and " +
                data.get("faithPoints").getAsInt() + " faith points!");
        else text.setText("You are entitled to " + resources + " starting resources!");
    }

    private void enableConfirm(){
        confirmBtn.setDisable((selected[0] + selected[1] + selected[2] + selected[3]) != resources);
    }

    public void incCoin(){
        selected[0]++;
        decCoinBtn.setDisable(false);
        coin.setText(String.valueOf(selected[0]));
        enableConfirm();
    }

    public void incStone(){
        selected[1]++;
        decStoneBtn.setDisable(false);
        stone.setText(String.valueOf(selected[1]));
        enableConfirm();
    }

    public void incShield(){
        selected[2]++;
        decShieldBtn.setDisable(false);
        shield.setText(String.valueOf(selected[2]));
        enableConfirm();
    }

    public void incServant(){
        selected[3]++;
        decServantBtn.setDisable(false);
        servant.setText(String.valueOf(selected[3]));
        enableConfirm();
    }

    public void decCoin(){
        selected[0]--;
        decCoinBtn.setDisable(selected[0] == 0);
        coin.setText(String.valueOf(selected[0]));
        enableConfirm();
    }

    public void decStone(){
        selected[1]--;
        decStoneBtn.setDisable(selected[1] == 0);
        stone.setText(String.valueOf(selected[1]));
        enableConfirm();
    }

    public void decShield(){
        selected[2]--;
        decShieldBtn.setDisable(selected[2] == 0);
        shield.setText(String.valueOf(selected[2]));
        enableConfirm();
    }

    public void decServant(){
        selected[3]--;
        decServantBtn.setDisable(selected[3] == 0);
        servant.setText(String.valueOf(selected[3]));
        enableConfirm();
    }

    public void confirmAction(){
        Resource[] placed = new Resource[10];

        for (int i = 0; i < 10; i++) {
            placed[i] = Resource.ANY;
        }

        if(resources == 1){
            for (int i = 0; i < 4; i++) {
                if(selected[i] != 0){
                    placed[0] = Resource.values()[i];
                    break;
                }
            }
        } else {
            int j = 0;
            for (int i = 0; i < 4; i++) {
                if(selected[i] == 1){
                    placed[j] = Resource.values()[i];
                    j++;
                } else if (selected[i] == 2){
                    placed[1] = Resource.values()[i];
                    placed[2] = Resource.values()[i];
                }
            }
        }

        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "CHOOSE_START_RESOURCES");
        payload.addProperty("playerID", JavaFxApp.getManager().getPlayerId());
        Gson gson = new Gson();
        String json = gson.toJson(new ArrayList<>(Arrays.asList(placed)));
        payload.addProperty("placed", json);

        JavaFxApp.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload));

        payload = new JsonObject();
        payload.addProperty("message", "Waiting for other players...");
        JavaFxApp.setRootWithData("loading", payload);
    }
}
