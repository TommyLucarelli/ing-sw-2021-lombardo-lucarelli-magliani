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

/**
 * Controller class for the resources choice scene.
 */
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
        ArrayList<Button> decButtons = new ArrayList<>(Arrays.asList(decCoinBtn, decStoneBtn, decShieldBtn, decServantBtn));
        ArrayList<Button> incButtons = new ArrayList<>(Arrays.asList(incCoinBtn, incStoneBtn, incShieldBtn, incServantBtn));
        ArrayList<TextField> textFields = new ArrayList<>(Arrays.asList(coin, stone, shield, servant));

        for (int i = 0; i < decButtons.size(); i++) {
            decButtons.get(i).setDisable(true);
            int index = i;
            decButtons.get(i).setOnAction(evt -> {
                selected[index]--;
                decButtons.get(index).setDisable(selected[index] == 0);
                textFields.get(index).setText(String.valueOf(selected[index]));
                enableConfirm();
            });
        }

        for (int i = 0; i < incButtons.size(); i++) {
            int index = i;
            incButtons.get(i).setOnAction(evt -> {
                selected[index]++;
                decButtons.get(index).setDisable(false);
                textFields.get(index).setText(String.valueOf(selected[index]));
                enableConfirm();
            });
        }

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

    /**
     * Method called everytime a resource is increased or decreased. Enables/disables the confirm button if the amount
     * of selected resources equals the amount of resources to select.
     */
    private void enableConfirm(){
        confirmBtn.setDisable((selected[0] + selected[1] + selected[2] + selected[3]) != resources);
    }

    /**
     * onAction method: sends to the server the selected resources.
     */
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

        JsonObject data = new JsonObject();
        data.addProperty("message", "Waiting for other players...");
        JavaFxApp.setRootWithData("loading", data);

        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "CHOOSE_START_RESOURCES");
        payload.addProperty("playerID", JavaFxApp.getManager().getMyself().getPlayerID());
        Gson gson = new Gson();
        String json = gson.toJson(new ArrayList<>(Arrays.asList(placed)));
        payload.addProperty("placed", json);

        JavaFxApp.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload));
    }
}
