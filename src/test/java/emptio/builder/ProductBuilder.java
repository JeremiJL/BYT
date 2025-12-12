package emptio.builder;

import emptio.domain.common.Category;
import emptio.domain.common.Cost;
import emptio.domain.common.Currency;
import emptio.domain.product.Product;
import emptio.domain.product.ProductService;
import emptio.domain.user.User;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
public class ProductBuilder {

    private final ProductService productService;

    private User seller;
    private Cost price;
    private byte[] image;
    private Category category;
    private String title;
    private String description;
    private int countOnMarketplace;

    public ProductBuilder(ProductService productService, UserBuilder userBuilder) {
        this.productService = productService;
        this.seller = userBuilder.build();
        this.price = new Cost(BigDecimal.TEN, Currency.EUR);
        this.image = new byte[]{1,2,3,4};
        this.category = Category.CLOTHING;
        this.title = "Title";
        this.description = "Description";
        this.countOnMarketplace = 10;

    }

    public Product build() {
        return productService.newProduct(seller, price,image,category,title,description,countOnMarketplace);
    }
}
