package emptio.domain.product;

import emptio.builder.AddressBuilder;
import emptio.builder.ProductBuilder;
import emptio.builder.UserBuilder;
import emptio.domain.DomainRepository;
import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.common.Category;
import emptio.domain.common.Cost;
import emptio.domain.common.Currency;
import emptio.domain.product.validators.*;
import emptio.domain.user.UserService;
import emptio.serialization.InMemoryCredentialsRepository;
import emptio.serialization.InMemoryDomainRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductTest {

    DomainRepository<Product> productRepository;
    Set<Validator<Product>> validators;

    ProductService productService;
    UserBuilder userBuilder;
    ProductBuilder productBuilder;
    UserService userService;
    AddressBuilder addressBuilder;

    @BeforeEach
    void setUp() {
        productRepository = new InMemoryDomainRepository<>();
        validators = new HashSet<>();
        productService = new ProductService(validators, productRepository);
        userService = new UserService(new InMemoryDomainRepository<>(), new InMemoryCredentialsRepository(), new HashSet<>());
        addressBuilder = new AddressBuilder();
        userBuilder = new UserBuilder(userService, addressBuilder);
        productBuilder =  new ProductBuilder(productService, userBuilder);
    }

    @Test
    void shouldValidatePrice() {
        validators.add(new PriceValidator());
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(NullPointerException.class, () -> {
            productBuilder.setPrice(new Cost(null, Currency.PLN));
            productBuilder.build();
        });
        assertThrows(NullPointerException.class, () -> {
            productBuilder.setPrice(new Cost(BigDecimal.ONE, null));
            productBuilder.build();
        });
        assertThrows(ValidationException.class, () -> {
            productBuilder.setPrice(new Cost(BigDecimal.valueOf(-2d), Currency.PLN));
            productBuilder.build();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        productBuilder.setPrice(new Cost(BigDecimal.valueOf(20.5d), Currency.PLN));
        productBuilder.build();
    }

    @Test
    void shouldValidateImage() {
        validators.add(new ImageValidator());
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(NullPointerException.class, () -> {
            productBuilder.setImage(null);
            productBuilder.build();
        });
        assertThrows(ValidationException.class, () -> {
            productBuilder.setImage(new byte[]{});
            productBuilder.build();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        productBuilder.setImage(new byte[]{1,2,3,4});
        productBuilder.build();
    }

    @Test
    void shouldValidateCategory() {
        validators.add(new CategoryValidator());
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(NullPointerException.class, () -> {
            productBuilder.setCategory(null);
            productBuilder.build();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        productBuilder.setCategory(Category.CLOTHING);
        productBuilder.build();
    }

    @Test
    void shouldValidateTitle() {
        TitleValidator titleValidator = new TitleValidator();
        validators.add(titleValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(NullPointerException.class, () -> {
            productBuilder.setTitle(null);
            productBuilder.build();
        });
        assertThrows(ValidationException.class, () -> {
            productBuilder.setTitle("");
            productBuilder.build();
        });
        assertThrows(ValidationException.class, () -> {
            productBuilder.setTitle(" ");
            productBuilder.build();
        });
        assertThrows(ValidationException.class, () -> {
            productBuilder.setTitle(stringOfGivenLength(titleValidator.maxCharacters + 1));
            productBuilder.build();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        assertThrows(ValidationException.class, () -> {
            productBuilder.setTitle("Waterproof vest for outdoors activities");
            productBuilder.build();
        });
    }

    @Test
    void shouldValidateDescription() {
        DescriptionValidator descriptionValidator = new DescriptionValidator();
        validators.add(descriptionValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(NullPointerException.class, () -> {
            productBuilder.setDescription(null);
            productBuilder.build();
        });
        assertThrows(ValidationException.class, () -> {
            productBuilder.setDescription("");
            productBuilder.build();
        });
        assertThrows(ValidationException.class, () -> {
            productBuilder.setDescription(" ");
            productBuilder.build();
        });
        assertThrows(ValidationException.class, () -> {
            productBuilder.setDescription(stringOfGivenLength(descriptionValidator.maxCharacters + 1));
            productBuilder.build();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        assertThrows(ValidationException.class, () -> {
            productBuilder.setDescription("Waterproof vest for outdoors activities, " +
                    "made from artificial cancerous fabric. Unisex. No returns. Recommended by 9 out of 10 dentists.");
            productBuilder.build();
        });
    }

    @Test
    void shouldValidateCountOnMarketplace() {
        validators.add(new CountOnMarketplaceValidator());
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            productBuilder.setCountOnMarketplace(-1);
            productBuilder.build();
        });

        // Decrementing
        productBuilder.setCountOnMarketplace(5);
        Product myProduct = productBuilder.build();
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
        Product myProduct = productBuilder.build();
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

    private String stringOfGivenLength(int length) {
        return "-".repeat(length);
    }

}


