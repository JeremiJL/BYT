package emptio.domain.user.validators;

import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.user.User;

public class SurnameValidator implements Validator<User> {

    public final int maxCharacters = 50;

    @Override
    public void validate(User entity) throws ValidationException {
        if (entity.surname == null)
            throw new ValidationException("Surname can't be null.");
        if (entity.surname.isBlank())
            throw new ValidationException("Surname is required - can't be empty.");
        if (entity.surname.length() > maxCharacters)
            throw new ValidationException("Surname can't be longer than " + maxCharacters + ".");
    }
}
