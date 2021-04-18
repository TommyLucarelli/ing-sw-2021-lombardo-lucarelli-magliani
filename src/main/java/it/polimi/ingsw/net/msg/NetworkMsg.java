package it.polimi.ingsw.net.msg;

import java.io.Serializable;
import java.util.UUID;

public abstract class NetworkMsg implements Serializable {
    UUID identifier = UUID.randomUUID();

    public UUID getIdentifier() {
        return identifier;
    }
}
