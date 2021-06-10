package it.polimi.ingsw.view.gui.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.core.model.Resource;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.cli.InputHandler;
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

        ArrayList<Button> buttons = new ArrayList<>(Arrays.asList(c0, c1, c2, c3, l0, l1, l2));
        ArrayList<Rectangle> rectangles = new ArrayList<>(Arrays.asList(rc0, rc1, rc2, rc3, rl0, rl1, rl2));

        for (int i = 0; i < buttons.size(); i++) {
            int index = i;
            buttons.get(i).setOnAction(evt -> pick(index > 3, (index < 4) ? index : (index - 4)));
            buttons.get(i).setOnMouseEntered(evt -> rectangles.get(index).setVisible(true));
            buttons.get(i).setOnMouseExited(evt -> rectangles.get(index).setVisible(false));
        }
    }

    public void comebackAction(){
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "COME_BACK");
        JavaFxApp.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload));

    }

    private void pick(boolean lineOrColumn, int index){
        JsonObject payload = new JsonObject();

        if ((JavaFxApp.getManager().getMyself().getCompactBoard().getAbilityActivationFlag()[2] != 0) && (JavaFxApp.getManager().getMyself().getCompactBoard().getAbilityActivationFlag()[3] != 0)) {
            payload.addProperty("gameAction", "PICK");
            payload.addProperty("choice", lineOrColumn ? "l" : "c");
            payload.addProperty("number", index);
            payload.addProperty("whiteMarbles", JavaFxApp.getManager().getCompactMarket().getWhites(!lineOrColumn, index));
            JavaFxApp.showPopupWithData("whitemarble", payload);
        } else {
            payload.addProperty("gameAction", "PICK");
            payload.addProperty("choice", lineOrColumn ? "l" : "c");
            payload.addProperty("number", index);
            JavaFxApp.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload));
        }
        Stage stage = (Stage) c0.getScene().getWindow();
        stage.close();
    }
}
