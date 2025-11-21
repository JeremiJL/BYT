package emptio.domain.campaign;

import emptio.serialization.Identifiable;

public class Campaign implements Identifiable {

    private int id;
    final String name;
    final Placement placement;
    final double pricePerInteraction;
    final double totalBudget;

    final double budgetSpent;
    final int interactionsCount;

    public Campaign(
            String name, Placement placement,
            double pricePerInteraction, double totalBudget, double budgetSpent, int interactionsCount) {
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


