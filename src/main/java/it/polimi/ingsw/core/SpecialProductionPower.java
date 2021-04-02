package it.polimi.ingsw.core;

public class SpecialProductionPower implements SpecialAbility {
    private Resource resource;
    private boolean isActivated;

    public SpecialProductionPower(Resource resource) {
        this.resource = resource;
        this.isActivated = false;
    }

    @Override
    public String getDescription() {
        return "Questà abilità fornisce un potere di produzione aggiuntivo. Quando attivi la produzione, puoi liberamente attivare anche questo potere, " +
                "secondo le regole normali. Riceverai una risorsa a scelta e 1 punto fede.";
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
