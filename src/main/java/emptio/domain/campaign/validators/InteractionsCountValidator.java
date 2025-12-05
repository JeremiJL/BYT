package emptio.domain.campaign.validators;

import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.campaign.Campaign;

public class InteractionsCountValidator implements Validator<Campaign> {

    @Override
    public void validate(Campaign entity) throws ValidationException {
        if (entity.interactionsCount < 0)
            throw new ValidationException("Interactions count can't be smaller than 0.");
    }
}
