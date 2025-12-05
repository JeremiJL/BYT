package emptio.domain.campaign.validators;

import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.campaign.Campaign;

public class NameValidator implements Validator<Campaign> {

    public final int maxCharacters = 50;

    @Override
    public void validate(Campaign entity) throws ValidationException {
        if (entity.getName() == null)
            throw new ValidationException("Name can't be null.");
        if (entity.getName().isBlank())
            throw new ValidationException("Name can't be blank.");
        if (entity.getName().length() > maxCharacters)
            throw new ValidationException("Name can't be longer than " + maxCharacters + ".");
    }
}
