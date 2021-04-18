package it.polimi.ingsw.core.model;

/**
 * Is it possible to delete this class and add a flag in the Warehouse class?
 */

public class SpecialWarehouse extends SpecialAbility{

    protected SpecialWarehouse(Resource resource){
        super(resource);
    }

    @Override
    public String getDescription() {
        return "Questa abilità speciale fornisce un deposito speciale di 2 spazi. Quando prendi risorse al mercato, puoi piazzare qui solo le risorse indicate." +
                "Puoi anche piazzare la stessa tipologia di risorsa in uno dei depositi base del magazzino.";
    }

    @Override
    public void activate() {
        super.activate();
    }
}
