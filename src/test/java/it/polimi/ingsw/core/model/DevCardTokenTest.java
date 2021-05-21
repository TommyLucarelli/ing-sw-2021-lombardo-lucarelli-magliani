package it.polimi.ingsw.core.model;

import it.polimi.ingsw.core.model.Colour;
import it.polimi.ingsw.core.model.DevCardToken;
import org.junit.Test;

import static org.junit.Assert.*;

public class DevCardTokenTest {

    @Test
    public void getAction() {
        DevCardToken dt = new DevCardToken(Colour.GREEN);
        String s1 = "DTGREEN";
        String s2;

        //s2 = dt.getAction();

        //assertEquals(s1,s2);
    }
}