package emptio.domain.user.validators;

import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.user.User;

public class SurnameValidator implements Validator<User> {

    public final int maxCharacters = 50;

    @Override
    public void validate(User entity) throws ValidationException {
        if (entity.getSurname() == null)
            throw new ValidationException("Surname can't be null.");
        if (entity.getSurname().isBlank())
            throw new ValidationException("Surname is required - can't be empty.");
        if (entity.getSurname().length() > maxCharacters)
            throw new ValidationException("Surname can't be longer than " + maxCharacters + ".");
    }
}
