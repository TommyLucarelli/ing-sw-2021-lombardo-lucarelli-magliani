package it.polimi.ingsw.core;

public class SpecialProductionPower extends SpecialAbility {

    public SpecialProductionPower(Resource resource) {
        super(resource);
    }

    @Override
    public String getDescription() {
        return "Questà abilità fornisce un potere di produzione aggiuntivo. Quando attivi la produzione, puoi liberamente attivare anche questo potere, " +
                "secondo le regole normali. Riceverai una risorsa a scelta e 1 punto fede.";
    }

    @Override
    public void activate() {
        super.activate();
    }
}
