package emptio.domain.campaign.validators;

import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.campaign.Campaign;

public class PlacementValidator implements Validator<Campaign> {

    @Override
    public void validate(Campaign entity) throws ValidationException {
        if (entity.getPlacement() == null)
            throw new ValidationException("Placement can't be null.");
    }
}
