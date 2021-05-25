package it.polimi.ingsw.view.gui.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.gui.JavaFxApp;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

public class LeadersChoiceController implements DynamicController, Initializable {
    JsonObject data;
    int playerId;
    ArrayList<Integer> leaderCards;
    ArrayList<Integer> selectedCards = new ArrayList<>();

    @FXML
    Text text;

    @FXML
    Button btn1, btn2, btn3, btn4;

    @FXML
    ImageView img1, img2, img3, img4;

    @FXML
    Rectangle rect1, rect2, rect3, rect4;

    @FXML
    Button confirmBtn;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        confirmBtn.setDisable(true);
    }

    @Override
    public void setData(JsonObject data) {
        this.data = data;
        Gson gson = new Gson();
        String json = data.get("leaderCards").getAsString();
        leaderCards = gson.fromJson(json, new TypeToken<ArrayList<Integer>>(){}.getType());

        img1.setImage(new Image(getClass().getResourceAsStream("/images/cards/" + leaderCards.get(0) + ".png")));
        img2.setImage(new Image(getClass().getResourceAsStream("/images/cards/" + leaderCards.get(1) + ".png")));
        img3.setImage(new Image(getClass().getResourceAsStream("/images/cards/" + leaderCards.get(2) + ".png")));
        img4.setImage(new Image(getClass().getResourceAsStream("/images/cards/" + leaderCards.get(3) + ".png")));

        text.setText("You are player " + data.get("playerOrder").getAsString() + ". Choose your two leader cards!");
    }

    public void add1(){
        if(rect1.isVisible()){
            selectedCards.remove(leaderCards.get(0));
            rect1.setVisible(false);
        } else {
            selectedCards.add(leaderCards.get(0));
            rect1.setVisible(true);
        }
        enableConfirm();
    }

    public void add2(){
        if(rect2.isVisible()){
            selectedCards.remove(leaderCards.get(1));
            rect2.setVisible(false);
        } else {
            selectedCards.add(leaderCards.get(1));
            rect2.setVisible(true);
        }
        enableConfirm();
    }

    public void add3(){
        if(rect3.isVisible()){
            selectedCards.remove(leaderCards.get(2));
            rect3.setVisible(false);
        } else {
            selectedCards.add(leaderCards.get(2));
            rect3.setVisible(true);
        }
        enableConfirm();
    }

    public void add4(){
        if(rect4.isVisible()){
            selectedCards.remove(leaderCards.get(3));
            rect4.setVisible(false);
        } else {
            selectedCards.add(leaderCards.get(3));
            rect4.setVisible(true);
        }
        enableConfirm();
    }

    public void enableConfirm(){
        confirmBtn.setDisable(selectedCards.size() != 2);
    }

    public void confirmAction(){
        for (int i = 0; i < 2; i++) {
            leaderCards.remove(selectedCards.get(i));
        }

        int[] selectedLeaders = new int[2];
        int[] discardedLeaders = new int[2];

        for (int i = 0; i < 2; i++) {
            selectedLeaders[i] = selectedCards.get(i);
            discardedLeaders[i] = leaderCards.get(i);
        }

        JavaFxApp.getManager().firstSetup(data.get("playerID").getAsInt(), data.get("playerName").getAsString(), selectedLeaders);

        Gson gson = new Gson();

        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "CHOOSE_START_LEADERS");
        payload.addProperty("playerID", data.get("playerID").getAsInt());
        String json = gson.toJson(discardedLeaders);
        payload.addProperty("discardedLeaders", json);

        JavaFxApp.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload));

        JavaFxApp.setRoot("loading");
    }
}
