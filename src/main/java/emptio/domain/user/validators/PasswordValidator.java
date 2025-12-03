package emptio.domain.user.validators;

import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.user.User;

import java.util.Arrays;

public class PasswordValidator implements Validator<User> {

    public final int maxCharacters = 50;
    protected final int minCharacters = 8;
    protected final String[] easyPasswords = {"password", "123", "qwe", "admin"};

    @Override
    public void validate(User entity) throws ValidationException {
        if (entity.password == null)
            throw new ValidationException("Password can't be null.");
        if (entity.password.isBlank())
            throw new ValidationException("Password can not be empty.");
        if (entity.password.length() > maxCharacters)
            throw new ValidationException("Password can't be longer than " + maxCharacters + ".");
        if (entity.password.length() < minCharacters)
            throw new ValidationException("Password can't be longer than " + minCharacters + ".");
        if (Arrays.stream(easyPasswords).anyMatch(password -> password.equals(entity.password)))
            throw new ValidationException("Password can't be that easy to guess.");
    }
}
