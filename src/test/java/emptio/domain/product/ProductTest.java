package emptio.domain.product;

import emptio.domain.Repository;
import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.common.Category;
import emptio.domain.common.Cost;
import emptio.domain.common.Currency;
import emptio.domain.product.validators.*;
import emptio.domain.user.User;
import emptio.serialization.InMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductTest {

    Repository<Product> productRepository;
    Set<Validator<Product>> validators;

    ProductService productService;
    ProductBuilder productBuilder;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryRepository<>();
        validators = new HashSet<>();
        productBuilder =  new ProductBuilder();
        productService = new ProductService(validators, productRepository);
    }

    @Test
    void shouldValidatePrice() {
        validators.add(new PriceValidator());
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(NullPointerException.class, () -> {
            productBuilder.withPrice(new Cost(null, Currency.PLN)).newProduct();
        });
        assertThrows(NullPointerException.class, () -> {
            productBuilder.withPrice(new Cost(BigDecimal.ONE, null)).newProduct();
        });
        assertThrows(ValidationException.class, () -> {
            productBuilder.withPrice(new Cost(BigDecimal.valueOf(-2d), Currency.PLN)).newProduct();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        productBuilder.withPrice(new Cost(BigDecimal.valueOf(20.5d), Currency.PLN)).newProduct();
    }

    @Test
    void shouldValidateImage() {
        validators.add(new ImageValidator());
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(NullPointerException.class, () -> {
            productBuilder.withImage(null).newProduct();
        });
        assertThrows(ValidationException.class, () -> {
            productBuilder.withImage(new byte[]{}).newProduct();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        productBuilder.withImage(new byte[]{1,2,3,4}).newProduct();
    }

    @Test
    void shouldValidateCategory() {
        validators.add(new CategoryValidator());
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(NullPointerException.class, () -> {
            productBuilder.withCategory(null).newProduct();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        assertEquals(Category.CLOTHING, productBuilder.withCategory(Category.CLOTHING).newProduct().getCategory());
    }

    @Test
    void shouldValidateTitle() {
        TitleValidator titleValidator = new TitleValidator();
        validators.add(titleValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(NullPointerException.class, () -> {
            productBuilder.withTitle(null).newProduct();
        });
        assertThrows(ValidationException.class, () -> {
            productBuilder.withTitle("").newProduct();
        });
        assertThrows(ValidationException.class, () -> {
            productBuilder.withTitle(" ").newProduct();
        });
        assertThrows(ValidationException.class, () -> {
            productBuilder.withTitle(stringOfGivenLength(titleValidator.maxCharacters + 1)).newProduct();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        assertThrows(ValidationException.class, () -> {
            productBuilder.withTitle("Waterproof vest for outdoors activities").newProduct();
        });
    }

    @Test
    void shouldValidateDescription() {
        DescriptionValidator descriptionValidator = new DescriptionValidator();
        validators.add(descriptionValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(NullPointerException.class, () -> {
            productBuilder.withDescription(null).newProduct();
        });
        assertThrows(ValidationException.class, () -> {
            productBuilder.withDescription("").newProduct();
        });
        assertThrows(ValidationException.class, () -> {
            productBuilder.withDescription(" ").newProduct();
        });
        assertThrows(ValidationException.class, () -> {
            productBuilder.withDescription(stringOfGivenLength(descriptionValidator.maxCharacters + 1)).newProduct();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        assertThrows(ValidationException.class, () -> {
            productBuilder.withDescription("Waterproof vest for outdoors activities, " +
                    "made from artificial cancerous fabric. Unisex. No returns. Recommended by 9 out of 10 dentists.").newProduct();
        });
    }

    @Test
    void shouldValidateCountOnMarketplace() {
        validators.add(new CountOnMarketplaceValidator());
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            productBuilder.withCountOnMarketplace(-1).newProduct();
        });

        // Decrementing
        Product myProduct = productBuilder.withCountOnMarketplace(5).newProduct();
        assertEquals(5, myProduct.getCountOnMarketplace());

        int id = myProduct.getId();

        productService.decrementCountOnMarketplace(id);
        myProduct = productRepository.find(id);
        assertEquals(4, myProduct.getCountOnMarketplace());

        productService.decrementCountOnMarketplace(id);
        myProduct = productRepository.find(id);
        assertEquals(3, myProduct.getCountOnMarketplace());
    }

    @Test
    void shouldValidateInteractions() {
        validators.add(new InteractionsValidator());

        // Assert interaction's value upon initialization
        Product myProduct = productBuilder.newProduct();
        assertEquals(0, myProduct.getInteractions());

        // Incrementing
        int id = myProduct.getId();

        productService.incrementInteractionCount(id);
        myProduct = productRepository.find(id);
        assertEquals(1, myProduct.getInteractions());

        productService.incrementInteractionCount(id);
        myProduct = productRepository.find(id);
        assertEquals(2, myProduct.getInteractions());
     }


    private class ProductBuilder {

        private User seller = null;
        private Cost price = new Cost(BigDecimal.TEN, Currency.EUR);
        private byte[] image = new byte[]{1,2,3,4};
        private Category category = Category.CLOTHING;
        private String title = "Title";
        private String description = "Description";
        private int countOnMarketplace = 10;

        Product newProduct() {
            return productService.newProduct(seller, price,image,category,title,description,countOnMarketplace);
        }

        ProductBuilder withPrice(Cost price) {
            this.price = price;
            return this;
        }

         ProductBuilder withImage(byte[] image) {
            this.image = image;
            return this;
        }

         ProductBuilder withCategory(Category category){
            this.category = category;
            return this;
        }

         ProductBuilder withTitle(String title) {
            this.title = title;
            return this;
        }

        ProductBuilder withDescription(String description) {
            this.description = description;
            return this;
        }

        ProductBuilder withCountOnMarketplace(int countOnMarketplace) {
            this.countOnMarketplace = countOnMarketplace;
            return this;
        }
    }

    private String stringOfGivenLength(int length) {
        return "-".repeat(length);
    }

}


