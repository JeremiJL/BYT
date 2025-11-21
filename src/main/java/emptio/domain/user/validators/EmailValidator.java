package emptio.domain.user.validators;

import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.user.User;

public class EmailValidator implements Validator<User> {

    @Override
    public void validate(User entity) throws ValidationException {
        if (entity.email == null)
            throw new ValidationException("Email can't be null.");
        if (entity.email.isBlank())
            throw new ValidationException("Email is required - can't be empty.");
        if (!entity.email.contains("@"))
            throw new ValidationException("Email address is invalid.");
    }
}
