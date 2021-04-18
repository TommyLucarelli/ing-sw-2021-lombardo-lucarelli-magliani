package it.polimi.ingsw.core.model;

public class SpecialExtraResource extends SpecialAbility{

    public SpecialExtraResource(Resource resource) {
        super(resource);
    }

    @Override
    public String getDescription() {
        return "Quando ricevi risorse al mercato, ogni biglia bianca presente nella riga/colonna ti fornisce la risorsa indicata. Se giochi due Leader con questa abilità, " +
                "quando prendi risorse al mercato, devi scegliere quale risorsa (tra quelle fornite dai leader) prendere per ciascuna biglia bianca " +
                "(non puoi quindi prendere entrambe le risorse da una singola biglia bianca).";
    }

    @Override
    public void activate() {
        super.activate();
    }
}
