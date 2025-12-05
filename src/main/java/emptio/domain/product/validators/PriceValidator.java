package emptio.domain.product.validators;

import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.product.Product;

import java.math.BigDecimal;

public class PriceValidator implements Validator<Product> {

    @Override
    public void validate(Product entity) throws ValidationException {
        if (entity.getPrice().value() == null)
            throw new ValidationException("Cost value can't be null.");
        if (entity.getPrice().currency() == null)
            throw new ValidationException("Cost currency can't be null.");
        if (entity.getPrice().value().compareTo(BigDecimal.ZERO) <= 0)
            throw new ValidationException("Cost value can't be smaller or equal than zero.");
    }
}
