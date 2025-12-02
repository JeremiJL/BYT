package emptio.domain.product.validators;

import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.product.Product;

public class ImageValidator implements Validator<Product> {

    @Override
    public void validate(Product entity) throws ValidationException {
        if (entity.image == null)
            throw new ValidationException("Image can't be null.");
        if (entity.image.length == 0)
            throw new ValidationException("Image can't be represent in 0 bytes.");
    }
}
