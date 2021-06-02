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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.ResourceBundle;

public class MarketActionController implements Initializable, DynamicController {
    @FXML
    ImageView m0, m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12;

    @FXML
    Rectangle rc0, rc1, rc2, rc3, rl0, rl1, rl2;

    @FXML
    Button c0, c1, c2, c3, l0, l1, l2;

    ArrayList<ImageView> marbleImages = new ArrayList<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        marbleImages.addAll(Arrays.asList(m0, m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12));
    }

    @Override
    public void setData(JsonObject data) {
        Gson gson = new Gson();
        String json = data.get("marbles").getAsString();
        int[] marbles = gson.fromJson(json, new TypeToken<int[]>(){}.getType());

        for (int i = 0; i < 13; i++) {
            marbleImages.get(i).setImage(new Image(getClass().getResourceAsStream("/images/marbles/" + marbles[i] + ".png")));
        }
    }

    public void selectc0(){
        pick(false, 0);
    }

    public void selectc1(){
        pick(false, 1);
    }

    public void selectc2(){
        pick(false, 2);
    }

    public void selectc3(){
        pick(false, 3);
    }

    public void selectl0(){
        pick(true, 0);
    }

    public void selectl1(){
        pick(true, 1);
    }

    public void selectl2(){
        pick(true, 2);
    }

    public void showrc0(){
        rc0.setVisible(true);
    }

    public void hiderc0(){
        rc0.setVisible(false);
    }

    public void showrc1(){
        rc1.setVisible(true);
    }

    public void hiderc1(){
        rc1.setVisible(false);
    }

    public void showrc2(){
        rc2.setVisible(true);
    }

    public void hiderc2(){
        rc2.setVisible(false);
    }

    public void showrc3(){
        rc3.setVisible(true);
    }

    public void hiderc3(){
        rc3.setVisible(false);
    }

    public void showrl0(){
        rl0.setVisible(true);
    }

    public void hiderl0(){
        rl0.setVisible(false);
    }

    public void showrl1(){
        rl1.setVisible(true);
    }

    public void hiderl1(){
        rl1.setVisible(false);
    }

    public void showrl2(){
        rl2.setVisible(true);
    }

    public void hiderl2(){
        rl2.setVisible(false);
    }

    public void comebackAction(){
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "COME_BACK");
        JavaFxApp.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload));

    }

    private void pick(boolean lineOrColumn, int index){
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "PICK");
        payload.addProperty("choice", lineOrColumn ? "l" : "c");
        payload.addProperty("number", index);
        JavaFxApp.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload));

        Stage stage = (Stage) c0.getScene().getWindow();
        stage.close();
    }
}
