package emptio.domain.campaign;

import emptio.domain.common.Blockable;
import emptio.domain.common.Cost;
import emptio.serialization.Identifiable;

public class Campaign extends Blockable implements Identifiable {

    private int id;
    public final String name;
    public final Placement placement;
    public final Cost pricePerInteraction;
    public final Cost totalBudget;

    public final Cost budgetSpent;
    public final int interactionsCount;

    public Campaign(
            String name, Placement placement,
            Cost pricePerInteraction, Cost totalBudget, Cost budgetSpent, int interactionsCount) {
        this.name = name;
        this.placement = placement;
        this.pricePerInteraction = pricePerInteraction;
        this.totalBudget = totalBudget;
        this.budgetSpent = budgetSpent;
        this.interactionsCount = interactionsCount;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public int getId() {
        return id;
    }
}


