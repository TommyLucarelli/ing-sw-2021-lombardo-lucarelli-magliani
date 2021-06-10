package it.polimi.ingsw.view;

import it.polimi.ingsw.net.msg.RequestMsg;

/**
 * Interface representing the user interface used to handle the requests from the server and their responses. It is
 * implemented by the CLI and the GUI.
 */
public interface UserInterface {
    void handleRequest(RequestMsg request);

    void connectionError();
}
