package emptio.domain.user.validators;

import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.user.User;

public class NameValidator implements Validator<User> {

    int maxCharacters = 50;

    @Override
    public void validate(User entity) throws ValidationException {
        if (entity.name == null)
            throw new ValidationException("Name can't be null.");
        if (entity.name.isBlank())
            throw new ValidationException("Password can not be empty.");
        if (entity.name.length() > maxCharacters)
            throw new ValidationException("Name can't be longer than " + maxCharacters + ".");
    }
}
