package emptio.domain.product;

import emptio.domain.Repository;
import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.common.Category;
import emptio.domain.common.Cost;

import java.util.Set;

public class ProductService {

    static Set<Validator<Product>> validators;
    static Repository<Product> productRepository;

    public ProductService(Set<Validator<Product>> validators, Repository<Product> productRepository) {
        ProductService.validators = validators;
        ProductService.productRepository = productRepository;
    }

    public Product newProduct(
            Cost price, byte[] image, Category category,
            String title, String description, int countOnMarketplace)
            throws ValidationException
    {
        Product product = new Product(Product.idService.getNewId(),
                price, image, category, title, description, 0.0, countOnMarketplace, 0);

        try {
            validators.forEach(validator -> validator.validate(product));
        } catch (ValidationException e) {
            throw new ValidationException("Failed to create a campaign with given parameters, cause : " + e.getMessage());
        }

        return productRepository.find(
                productRepository.add(product)
        );
    }

    public void incrementInteractionCount(int id) {
        Product oldProduct = productRepository.find(id);
        Product updatedProduct = oldProduct.withInteractions(oldProduct.getInteractions() + 1);
        productRepository.update(updatedProduct);
    }

    public void decrementCountOnMarketplace(int id) {
        Product oldProduct = productRepository.find(id);

        if (oldProduct.getCountOnMarketplace() > 0) {
            Product updatedProduct = oldProduct.withCountOnMarketplace(oldProduct.getCountOnMarketplace() - 1);
            productRepository.update(updatedProduct);
        }
        else {
            throw new ProductServiceException("Can't decrement count off products on marketplace, it's already 0.");
        }
    }

}

