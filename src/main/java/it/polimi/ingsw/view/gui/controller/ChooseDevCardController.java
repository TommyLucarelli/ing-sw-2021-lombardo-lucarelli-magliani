package it.polimi.ingsw.view.gui.controller;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.gui.JavaFxApp;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Arrays;

public class ChooseDevCardController implements DynamicController {
    @FXML
    ImageView c11, c12, c13, c14, c21, c22, c23, c24, c31, c32, c33, c34;

    @FXML
    Text problem;

    @Override
    public void setData(JsonObject data) {
        ArrayList<ImageView> cards = new ArrayList<>(Arrays.asList(c11, c12, c13, c14, c21, c22, c23, c24, c31, c32, c33, c34));

        Gson gson = new Gson();

        if(data.has("problem")){
            problem.setVisible(true);
        }

        String json = data.get("structure").getAsString();
        int[][] structure = gson.fromJson(json, new TypeToken<int[][]>(){}.getType());

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 4; j++) {
                if(structure[i][j] != 0)
                    cards.get((i * 4) + j).setImage(new Image(getClass().getResourceAsStream("/images/cards/" + structure[i][j] + ".png")));
            }
        }
    }

    public void comebackAction(){
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "COME_BACK");

        JavaFxApp.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload));

        Stage stage = (Stage) c11.getScene().getWindow();
        stage.close();
    }

    private void choose(int row, int column){
        JsonObject payload = new JsonObject();
        payload.addProperty("gameAction", "CHOOSE_DEVCARD");
        payload.addProperty("line", row - 1);
        payload.addProperty("column", column - 1);

        JavaFxApp.send(new ResponseMsg(null, MessageType.GAME_MESSAGE, payload));

        Stage stage = (Stage) c11.getScene().getWindow();
        stage.close();
    }

    public void choose11(){
        choose(1, 1);
    }

    public void choose12(){
        choose(1, 2);
    }

    public void choose13(){
        choose(1, 3);
    }

    public void choose14(){
        choose(1, 4);
    }

    public void choose21(){
        choose(2, 1);
    }

    public void choose22(){
        choose(2, 2);
    }

    public void choose23(){
        choose(2, 3);
    }

    public void choose24(){
        choose(2, 4);
    }

    public void choose31(){
        choose(3, 1);
    }

    public void choose32(){
        choose(3, 2);
    }

    public void choose33(){
        choose(3, 3);
    }

    public void choose34(){
        choose(3, 4);
    }
}
