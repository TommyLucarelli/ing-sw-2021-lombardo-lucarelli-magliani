package it.polimi.ingsw.core;

public class SpecialExtraResource implements SpecialAbility{
    private final Resource resource;
    private boolean isActivated;

    public SpecialExtraResource(Resource resource) {
        this.resource = resource;
        this.isActivated = false;
    }

    @Override
    public String getDescription() {
        return "Quando ricevi risorse al mercato, ogni biglia bianca presente nella riga/colonna ti fornisce la risorsa indicata. Se giochi due Leader con questa abilità, " +
                "quando prendi risorse al mercato, devi scegliere quale risorsa (tra quelle fornite dai leader) prendere per ciascuna biglia bianca " +
                "(non puoi quindi prendere entrambe le risorse da una singola biglia bianca).";
    }

    @Override
    public Resource getResource() {
        return this.resource;
    }

    @Override
    public boolean isActivated() {
        return this.isActivated;
    }

    @Override
    public void activate() {
        this.isActivated = true;
    }
}
