package emptio.domain.product;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import emptio.domain.common.Blockable;
import emptio.domain.common.Category;
import emptio.domain.common.Cost;
import emptio.domain.user.Merchant;
import emptio.serialization.Identifiable;
import lombok.*;

import java.util.Collections;

@EqualsAndHashCode(callSuper = true)
@Value public class Product extends Blockable implements Identifiable {

    int id;
    @NonNull Cost price;
    @NonNull byte[] image;
    @NonNull Category category;
    @NonNull String title;
    @NonNull String description;
    double orderingWeight;
    @With int countOnMarketplace;
    @With int interactions;
    @NonNull Merchant seller;

    @JsonCreator
    public Product(@JsonProperty("id") int id,
                   @JsonProperty("price") @NonNull Cost price,
                   @JsonProperty("image") @NonNull byte[] image,
                   @JsonProperty("category") @NonNull Category category,
                   @JsonProperty("title") @NonNull String title,
                   @JsonProperty("description") @NonNull String description,
                   @JsonProperty("orderingWeight") double orderingWeight,
                   @JsonProperty("countOnMarketplace") int countOnMarketplace,
                   @JsonProperty("interactions") int interactions,
                   @JsonProperty("seller") @NonNull Merchant seller) {
        this.seller = seller.withProducts(
                Collections.emptySet()
        ); // to end referencing loop
        this.id = id;
        this.price = price;
        this.image = image;
        this.category = category;
        this.title = title;
        this.description = description;
        this.orderingWeight = orderingWeight;
        this.countOnMarketplace = countOnMarketplace;
        this.interactions = interactions;
    }
}
