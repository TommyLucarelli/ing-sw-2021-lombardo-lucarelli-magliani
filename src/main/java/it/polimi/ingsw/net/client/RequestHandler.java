package it.polimi.ingsw.net.client;

import it.polimi.ingsw.net.msg.RequestMsg;

public interface RequestHandler {
    void handleRequest(RequestMsg request);
}
