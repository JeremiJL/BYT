package emptio.domain.user.validators;

import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.user.User;

public class LoginValidator implements Validator<User> {

    public final int maxCharacters = 50;

    @Override
    public void validate(User entity) throws ValidationException {
        if (entity.login == null)
            throw new ValidationException("Login can't be null.");
        if (entity.login.isBlank())
            throw new ValidationException("Password can not be empty.");
        if (entity.login.length() > maxCharacters)
            throw new ValidationException("Name can't be longer than " + maxCharacters + ".");
    }
}
