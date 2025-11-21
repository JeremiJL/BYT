package emptio.domain.user.validators;

import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.user.Address;
import emptio.domain.user.User;

import java.util.Set;

public class AddressValidator implements Validator<User> {

    private final Set<Validator<Address>> validators;

    public AddressValidator(Set<Validator<Address>> validators) {
        this.validators = validators;
    }

    @Override
    public void validate(User entity) throws ValidationException {
        if (entity.address == null)
            throw new ValidationException("Name can't be null.");
        validators.forEach(validator -> validator.validate(entity.address));
    }
}


