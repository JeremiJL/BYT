package emptio.domain.campaign;

import emptio.domain.common.Blockable;
import emptio.domain.common.Cost;
import emptio.serialization.Identifiable;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Value;
import lombok.With;

@EqualsAndHashCode(callSuper = true)
@Value public class Campaign extends Blockable implements Identifiable {
    int id;
    String name;
    Placement placement;
    Cost pricePerInteraction;
    Cost totalBudget;
    @With Cost budgetSpent;
    @With int interactionsCount;
}


