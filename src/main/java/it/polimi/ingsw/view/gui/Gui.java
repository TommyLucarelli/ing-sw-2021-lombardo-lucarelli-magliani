package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.net.client.Client;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.UserInterface;
import javafx.application.Application;
import javafx.application.Platform;

public class Gui implements UserInterface {
    private Client client;

    public Gui(Client client){
        this.client = client;
        (new Thread(() -> Application.launch((JavaFxApp.class)))).start();
        JavaFxApp.setManager(this);
    }

    //TODO: gestire gli errori di input da parte dell'utente: username già utilizzato, lobby non esistente, ecc...

    @Override
    public void handleRequest(RequestMsg request) {
        switch (request.getMessageType()) {
            case REGISTRATION_MESSAGE:
                Platform.runLater(() -> JavaFxApp.setRoot("registration"));
                break;
            case WELCOME_MESSAGE:
                Platform.runLater(() -> {
                    JavaFxApp.setRoot("welcome");
                    JavaFxApp.setData("username", request.getPayload().get("username").getAsString());
                });
                break;
            case NUMBER_OF_PLAYERS:
                Platform.runLater(() -> JavaFxApp.setRoot("creategame"));
                break;
            case JOIN_GAME:
                Platform.runLater(() -> JavaFxApp.setRoot("joinlobby"));
                break;
            case GAME_MESSAGE:
                switch (request.getPayload().get("gameAction").getAsString()){
                    case "WAIT_FOR_PLAYERS":
                    case "WAIT_START_GAME":
                        Platform.runLater(() -> JavaFxApp.setRootWithData("waitplayers", request.getPayload()));
                        break;
                    case "SHORT_UPDATE":
                    case "START_GAME_COMMAND":
                        Platform.runLater(() -> JavaFxApp.setData(request.getPayload()));
                        break;
                }
        }
    }

    public void send(ResponseMsg responseMsg){
        client.send(responseMsg);
    }

    public void start(){
        this.client.run();
    }

    public void close(){
        this.client.closeConnection();
        System.exit(0);
    }
}
