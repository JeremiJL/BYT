package emptio.domain.product;

import emptio.domain.common.Blockable;
import emptio.domain.common.Category;
import emptio.domain.common.Cost;
import emptio.domain.user.Merchant;
import emptio.domain.user.User;
import emptio.serialization.Identifiable;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Value public class Product extends Blockable implements Identifiable {

    @NonNull Merchant seller;
    int id;
    @NonNull Cost price;
    @NonNull byte[] image;
    @NonNull Category category;
    @NonNull String title;
    @NonNull String description;
    double orderingWeight;
    @With int countOnMarketplace;
    @With int interactions;
}


