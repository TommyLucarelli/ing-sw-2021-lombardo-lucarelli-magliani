package it.polimi.ingsw.net.client;

import it.polimi.ingsw.net.msg.MessageType;
import it.polimi.ingsw.net.msg.ResponseMsg;

import java.util.TimerTask;

/**
 * Class implementing a task to ping the server.
 */
public class Ping extends TimerTask {
    private Client client;

    /**
     * Class constructor
     * @param client the client of the task
     */
    protected Ping(Client client){
        super();
        this.client = client;
    }

    @Override
    public void run(){
        client.send(new ResponseMsg(null, MessageType.PING, null));
    }
}
