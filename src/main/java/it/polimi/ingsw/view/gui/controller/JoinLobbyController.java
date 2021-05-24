package it.polimi.ingsw.view.gui.controller;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class JoinLobbyController {
    @FXML
    TextField lobbyId;

    public void joinLobby(){
        System.out.println(lobbyId.getText());
    }
}
