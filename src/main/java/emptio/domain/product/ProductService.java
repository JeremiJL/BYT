package emptio.domain.product;

import emptio.domain.*;
import emptio.domain.common.Category;
import emptio.domain.common.Cost;
import emptio.domain.user.Merchant;
import emptio.domain.user.UserService;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ProductService {

    private final Set<Validator<Product>> validators;
    private final DomainRepository<Product> productRepository;
    private final UserService userService;

    @SafeVarargs
    public ProductService(DomainRepository<Product> productRepository, UserService userService, Validator<Product>... validators) {
        this.validators = new HashSet<>(List.of(validators));
        this.userService = userService;
        this.productRepository = productRepository;
    }

    public ProductService(Set<Validator<Product>> validators, UserService userService, DomainRepository<Product> productRepository) {
        this.validators = validators;
        this.productRepository = productRepository;
        this.userService = userService;
    }

    public Product newProduct(
            Merchant seller,
            Cost price, byte[] image, Category category,
            String title, String description, int countOnMarketplace)
            throws ValidationException, RepositoryException
    {
        if (image == null)
            image = getDefalutImage();

        Product product = new Product(Product.idService.getNewId(), price, image, category, title, description, 0.0, countOnMarketplace, 0, seller
        );

        try {
            validators.forEach(validator -> validator.validate(product));
        } catch (ValidationException e) {
            throw new ValidationException("Failed to create a campaign with given parameters, cause : " + e.getMessage());
        }

        userService.addProduct(seller,product);
        return productRepository.find(
                productRepository.add(product)
        );
    }

    public void incrementInteractionCount(int id) {
        Product oldProduct = productRepository.find(id);
        Product updatedProduct = oldProduct.withInteractions(oldProduct.getInteractions() + 1);
        productRepository.update(updatedProduct);
    }

    public void decrementCountOnMarketplace(int id) throws ProductServiceException {
        Product oldProduct = productRepository.find(id);

        if (oldProduct.getCountOnMarketplace() > 0) {
            Product updatedProduct = oldProduct.withCountOnMarketplace(oldProduct.getCountOnMarketplace() - 1);
            productRepository.update(updatedProduct);
        }
        else {
            throw new ProductServiceException("Can't decrement count off products on marketplace, it's already 0.");
        }
    }

    private byte[] getDefalutImage() {
        return new byte[]{1};
    }

}

