package it.polimi.ingsw.view.gui.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.polimi.ingsw.view.gui.JavaFxApp;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class EndingController implements DynamicController{
    @FXML
    Text singlePlayerMsg, rankings, winOrLose;

    StringBuilder rankingString = new StringBuilder();

    @Override
    public void setData(JsonObject data) {
        JsonArray players = data.get("players").getAsJsonArray();
        int x, vp;
        String name;
        boolean flag = false;

        if(data.has("finalScenario")){
            singlePlayerMsg.setVisible(true);
            switch (data.get("finalScenario").getAsInt()){
                case 1:
                case 2:
                    winOrLose.setText("You won!");
                    singlePlayerMsg.setText("Your score is: " + players.get(0).getAsJsonObject().get("victoryPoints").getAsInt());
                    break;
                case 3:
                    winOrLose.setText("You lost...");
                    singlePlayerMsg.setText("Lorenzo has reached the last spot on his Faith Track");
                    break;
                case 4:
                    winOrLose.setText("You lost...");
                    singlePlayerMsg.setText("One column of the Development Card Structure is empty");
                    break;
            }
        }else {
            for (JsonElement player : players) {
                x = player.getAsJsonObject().get("position").getAsInt();
                if (x == 1 && player.getAsJsonObject().get("playerID").getAsInt() == JavaFxApp.getManager().getMyself().getPlayerID()) {
                    winOrLose.setText("You won!");
                    flag = true;
                    break;
                }
            }
            if (!flag)
                winOrLose.setText("You lost...");
        }

        if(!data.has("finalScenario")) {
            rankings.setVisible(true);
            rankingString.append(rankings.getText());
            for (JsonElement player : players) {
                x = player.getAsJsonObject().get("position").getAsInt();
                vp = player.getAsJsonObject().get("victoryPoints").getAsInt();
                name = player.getAsJsonObject().get("name").getAsString();
                rankingString.append("\n").append(x).append(". ").append(name).append(" - VP: ").append(vp);
            }
            rankings.setText(rankingString.toString());
        }
    }

    /**
     * onAction method: closes the application.
     */
    public void quit(){
        JavaFxApp.close();
    }
}
