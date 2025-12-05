package emptio.domain.campaign;

import emptio.domain.common.Blockable;
import emptio.domain.common.Cost;
import emptio.domain.user.User;
import emptio.serialization.Identifiable;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Value public class Campaign extends Blockable implements Identifiable {
    int id;
    User owner;
    String name;
    Placement placement;
    Cost pricePerInteraction;
    Cost totalBudget;
    @With Cost budgetSpent;
    @With int interactionsCount;
}


