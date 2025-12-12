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
        if (entity.getPassword() == null)
            throw new ValidationException("Password can't be null.");
        if (entity.getPassword().isBlank())
            throw new ValidationException("Password can not be empty.");
        if (entity.getPassword().length() > maxCharacters)
            throw new ValidationException("Password can't be longer than " + maxCharacters + ".");
        if (entity.getPassword().length() < minCharacters)
            throw new ValidationException("Password can't be shorter than " + minCharacters + ".");
        if (Arrays.stream(easyPasswords).anyMatch(password -> password.equals(entity.getPassword())))
            throw new ValidationException("Password can't be that easy to guess.");
    }
}
