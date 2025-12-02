package emptio.domain.product;

import emptio.domain.common.Blockable;
import emptio.domain.common.Category;
import emptio.domain.common.Cost;
import emptio.serialization.Identifiable;

public class Product extends Blockable implements Identifiable {

    private int id;

    public final Cost price;
    public final byte[] image;
    public final Category category;
    public final String title;
    public final String description;
    public final double orderingWeight;
    public final int countOnMarketplace;
    public final int interactions;

    public Product(Cost price, byte[] image, Category category, String title, String description, double orderingWeight, int count, int interactions) {
        this.price = price;
        this.image = image;
        this.category = category;
        this.title = title;
        this.description = description;
        this.orderingWeight = orderingWeight;
        this.countOnMarketplace = count;
        this.interactions = interactions;
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


