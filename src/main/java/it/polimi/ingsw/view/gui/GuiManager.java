package it.polimi.ingsw.view.gui;

import it.polimi.ingsw.net.client.Client;
import it.polimi.ingsw.net.msg.RequestMsg;
import it.polimi.ingsw.net.msg.ResponseMsg;
import it.polimi.ingsw.view.UserInterface;
import javafx.application.Application;
import javafx.application.Platform;

import java.io.IOException;

public class GuiManager implements UserInterface {
    private Client client;

    public GuiManager(Client client){
        this.client = client;
        (new Thread(() -> Application.launch((Gui.class)))).start();
        Gui.setManager(this);
    }

    //TODO: gestire gli errori di input da parte dell'utente: username già utilizzato, lobby non esistente, ecc...

    @Override
    public void handleRequest(RequestMsg request) {
        switch (request.getMessageType()) {
            case REGISTRATION_MESSAGE:
                Platform.runLater(() -> Gui.setRoot("registration"));
                break;
            case WELCOME_MESSAGE:
                Platform.runLater(() -> Gui.setRoot("welcome"));
                break;
            case NUMBER_OF_PLAYERS:
                Platform.runLater(() -> Gui.setRoot("creategame"));
                break;
            case JOIN_GAME:
                Platform.runLater(() -> Gui.setRoot("joinlobby"));
                break;
            case GAME_MESSAGE:
                switch (request.getPayload().get("gameAction").getAsString()){
                    case "WAIT_FOR_PLAYERS":
                    case "WAIT_START_GAME":
                        Platform.runLater(() -> Gui.setRoot("waitplayers"));
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
