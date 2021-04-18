package it.polimi.ingsw.core.model;

import it.polimi.ingsw.core.model.Resource;
import it.polimi.ingsw.core.model.SpecialAbility;
import it.polimi.ingsw.core.model.SpecialDiscount;
import it.polimi.ingsw.core.model.SpecialWarehouse;
import org.junit.Test;

import static org.junit.Assert.*;

public class SpecialAbilityTest {

    @Test
    public void getResource() {
        SpecialAbility specialAbility = new SpecialDiscount(Resource.SHIELD);
        assertEquals(specialAbility.getResource(), Resource.SHIELD);
    }

    @Test
    public void isActivated() {
        SpecialAbility specialAbility = new SpecialWarehouse(Resource.SERVANT);
        assertFalse(specialAbility.isActivated());
        specialAbility.activate();
        assertTrue(specialAbility.isActivated());
    }
}