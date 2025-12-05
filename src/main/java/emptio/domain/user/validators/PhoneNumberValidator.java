package emptio.domain.user.validators;

import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.user.User;

public class PhoneNumberValidator implements Validator<User> {

    public final int maxCharacters = 9;
    public final int minCharacters = 9;
    protected final String[] possibleCharacters = {"0","1","2","3","4","5","6","7","8","9"};

    @Override
    public void validate(User entity) throws ValidationException {
        if (entity.getPhoneNumber() == null)
            throw new ValidationException("Phonenumber can't be null.");
        if (entity.getPhoneNumber().isBlank())
            throw new ValidationException("Phone number is required - can't be empty.");
        if (entity.getPhoneNumber().length() > maxCharacters)
            throw new ValidationException("Phone number can't be longer than " + maxCharacters + ".");
        if (entity.getPhoneNumber().length() < minCharacters)
            throw new ValidationException("Phone number can't be shorter than " + minCharacters + ".");
    }
}
