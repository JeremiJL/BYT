package emptio.domain.campaign;

import emptio.domain.common.Blockable;
import emptio.domain.common.Cost;
import emptio.domain.user.User;
import emptio.serialization.Identifiable;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Value public class Campaign extends Blockable implements Identifiable {
    @NonNull int id;
    @NonNull User owner;
    @NonNull String name;
    @NonNull Placement placement;
    @NonNull Cost pricePerInteraction;
    @NonNull Cost totalBudget;
    @NonNull @With Cost budgetSpent;
    @NonNull @With int interactionsCount;
}


