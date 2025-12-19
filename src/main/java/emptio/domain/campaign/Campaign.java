package emptio.domain.campaign;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import emptio.domain.common.Blockable;
import emptio.domain.common.Cost;
import emptio.domain.user.Advertiser;
import emptio.domain.user.User;
import emptio.serialization.Identifiable;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Value public class Campaign extends Blockable implements Identifiable {
    int id;
    @NonNull Advertiser owner;
    @NonNull String name;
    @NonNull Placement placement;
    @NonNull Cost pricePerInteraction;
    @NonNull Cost totalBudget;
    @NonNull @With Cost budgetSpent;
    @With int interactionsCount;

    @JsonCreator
    public Campaign(@JsonProperty("id") int id, @JsonProperty("owner") @NonNull Advertiser owner,
                    @JsonProperty("name") @NonNull String name, @JsonProperty("placement") @NonNull Placement placement,
                    @JsonProperty("pricePerInteraction") @NonNull Cost pricePerInteraction,
                    @JsonProperty("totalBudget") @NonNull Cost totalBudget,
                    @JsonProperty("budgetSpent") @NonNull Cost budgetSpent,
                    @JsonProperty("interactionsCount") int interactionsCount) {
        this.id = id;
        this.owner = owner;
        this.name = name;
        this.placement = placement;
        this.pricePerInteraction = pricePerInteraction;
        this.totalBudget = totalBudget;
        this.budgetSpent = budgetSpent;
        this.interactionsCount = interactionsCount;
    }
}


