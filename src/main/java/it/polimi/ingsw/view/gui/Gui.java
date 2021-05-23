package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.net.msg.ResponseMsg;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Gui extends Application {
    private static Scene scene;
    private static GuiManager manager;

    @Override
    public void start(Stage stage) throws Exception {
        scene = new Scene(loadFXML("opening"), 1200, 800);
        stage.setTitle("Masters of Renaissance");
        stage.setScene(scene);
        stage.getScene().getStylesheets().add(getClass().getResource("/css/master.css").toExternalForm());
        stage.show();
    }

    public static void setManager(GuiManager manager) {
        Gui.manager = manager;
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static void setRoot(String fxml){
        try {
            scene.setRoot(loadFXML(fxml));
        } catch (IOException e){
            System.err.println("IOException - FXML file not found");
        }
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Gui.class.getResource("/fxml/" + fxml + ".fxml"));
        return fxmlLoader.load();
    }

    public static void send(ResponseMsg responseMsg){
        manager.send(responseMsg);
    }
}