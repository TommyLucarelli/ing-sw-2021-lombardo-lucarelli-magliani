package it.polimi.ingsw.view.cli;

import org.junit.Test;

import static org.junit.Assert.*;

public class InputHandlerTest {
    @Test
    public void testGetString() {
        int aaa = InputHandler.getInt();
        System.out.println(aaa);
    }
}