package emptio.domain.product.validators;

import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.product.Product;

public class CategoryValidator implements Validator<Product> {

    @Override
    public void validate(Product entity) throws ValidationException {
        if (entity.category == null)
            throw new ValidationException("Category can't be null");
    }
}
