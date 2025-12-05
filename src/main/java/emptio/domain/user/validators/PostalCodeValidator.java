package emptio.domain.user.validators;

import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.user.Address;

public class PostalCodeValidator implements Validator<Address> {

    protected final int maxCharacters = 9;
    protected final int minCharacters = 6;
    
    @Override
    public void validate(Address entity) throws ValidationException {
        if (entity.getPostalCode() == null)
            throw new ValidationException("Postal code can't be null.");
        if (entity.getPostalCode().isBlank())
            throw new ValidationException("Postal code can not be empty.");
        if (entity.getPostalCode().length() > maxCharacters)
            throw new ValidationException("Postal code can't be longer than " + maxCharacters + ".");
        if (entity.getPostalCode().length() < minCharacters)
            throw new ValidationException("Postal code can't be shorter than " + minCharacters + ".");
    }
}
