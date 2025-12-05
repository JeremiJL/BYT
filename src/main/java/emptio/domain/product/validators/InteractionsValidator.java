package emptio.domain.product.validators;

import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.product.Product;

public class InteractionsValidator implements Validator<Product> {

    @Override
    public void validate(Product entity) throws ValidationException {
        if (entity.getInteractions() < 0)
            throw new ValidationException("Interactions number can't be smaller than 0");
    }
}
