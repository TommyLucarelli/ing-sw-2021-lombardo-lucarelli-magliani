package it.polimi.ingsw.view.gui.controller;

import it.polimi.ingsw.view.gui.Gui;
import it.polimi.ingsw.view.gui.GuiManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class SceneController {
    private final GuiManager manager;
    private Stage stage;

    public SceneController(GuiManager manager, Stage stage){
        this.manager = manager;
        this.stage = stage;
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void setScene(String fxmlFile) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/" + fxmlFile));
        stage.setScene(new Scene(root, 1200, 800));
        stage.show();
    }
}
