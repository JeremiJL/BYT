package emptio.domain.product.validators;

import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.product.Product;

public class DescriptionValidator implements Validator<Product> {

    public final int maxCharacters = 50;

    @Override
    public void validate(Product entity) throws ValidationException {
        if (entity.getDescription() == null)
            throw new ValidationException("Description can't be null.");
        if (entity.getDescription().isBlank())
            throw new ValidationException("Description can't be empty.");
        if (entity.getDescription().length() > maxCharacters)
            throw new ValidationException("Description can't be longer than " + maxCharacters + " characters.");
    }
}
