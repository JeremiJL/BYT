package emptio.domain.product.validators;

import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.product.Product;

public class CountOnMarketplaceValidator implements Validator<Product> {

    @Override
    public void validate(Product entity) throws ValidationException {
        if (entity.getCountOnMarketplace() < 0)
            throw new ValidationException("Count on marketplace can't be smaller than 0");
    }
}
