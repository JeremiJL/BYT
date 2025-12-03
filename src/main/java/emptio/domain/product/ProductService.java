package emptio.domain.product;

import emptio.domain.Repository;
import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.common.Category;
import emptio.domain.common.Cost;

import java.util.Set;

public class ProductService {

    Set<Validator<Product>> validators;
    Repository<Product> productRepository;

    public ProductService(Set<Validator<Product>> validators, Repository<Product> productRepository) {
        this.validators = validators;
        this.productRepository = productRepository;
    }

    public Product newProduct(
            Cost price, byte[] image, Category category,
            String title, String description, int countOnMarketplace)
            throws ValidationException
    {
        Product product = new Product(price, image, category, title, description, 0.0, countOnMarketplace, 0);

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
        Product updatedProduct = new Product(
                oldProduct.price, oldProduct.image,
                oldProduct.category, oldProduct.title,
                oldProduct.description, oldProduct.orderingWeight,
                oldProduct.countOnMarketplace, oldProduct.interactions + 1
        );
        updatedProduct.setId(oldProduct.getId());
        productRepository.update(updatedProduct);
    }

    public void decrementCountOnMarketplace(int id) {
        Product oldProduct = productRepository.find(id);

        if (oldProduct.countOnMarketplace > 0) {
            Product updatedProduct = new Product(
                    oldProduct.price, oldProduct.image,
                    oldProduct.category, oldProduct.title,
                    oldProduct.description, oldProduct.orderingWeight,
                    oldProduct.countOnMarketplace - 1, oldProduct.interactions
            );
            updatedProduct.setId(oldProduct.getId());
            productRepository.update(updatedProduct);
        }
        else {
            throw new ProductServiceException("Can't decrement count off products on marketplace, it's already 0.");
        }
    }

}

