package emptio.domain.product.validators;

import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.product.Product;

public class TitleValidator implements Validator<Product> {

    public final int maxCharacters = 20;

    @Override
    public void validate(Product entity) throws ValidationException {
        if (entity.getTitle() == null)
            throw new ValidationException("Title can't be null");
        if (entity.getTitle().isBlank())
            throw new ValidationException("Title can't be empty.");
        if (entity.getTitle().length() > maxCharacters)
            throw new ValidationException("Title can't be longer than " + maxCharacters + " characters.");
    }
}
