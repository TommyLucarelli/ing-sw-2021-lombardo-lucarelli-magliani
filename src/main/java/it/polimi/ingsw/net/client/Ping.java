package it.polimi.ingsw.net.client;

import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.ResponseMsg;

import java.util.TimerTask;

public class Ping extends TimerTask {
    private Client client;

    protected Ping(Client client){
        super();
        this.client = client;
    }

    @Override
    public void run(){
        client.send(new ResponseMsg(null, MessageType.PING, null));
    }
}
