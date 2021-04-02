package it.polimi.ingsw.core;

/**
 * Is it possible to delete this class and add a flag in the Warehouse class?
 */

public class SpecialWarehouse implements SpecialAbility{
    private Resource resource;

    protected SpecialWarehouse(Resource resource){
        this.resource = resource;
    }

    @Override
    public String getDescription() {
        return "Questa abilità speciale fornisce un deposito speciale di 2 spazi. Quando prendi risorse al mercato, puoi piazzare qui solo le risorse indicate." +
                "Puoi anche piazzare la stessa tipologia di risorsa in uno dei depositi base del magazzino.";
    }

    @Override
    public Resource getResource() {
        return this.resource;
    }

    @Override
    public void evokeEffect() {
        //TODO: decorator del Warehouse?
    }
}
