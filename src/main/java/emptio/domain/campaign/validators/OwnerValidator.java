package emptio.domain.campaign.validators;

import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.campaign.Campaign;
import emptio.domain.user.Advertiser;

public class OwnerValidator implements Validator<Campaign> {

    @Override
    public void validate(Campaign entity) throws ValidationException {
        if (entity.getOwner() == null)
            throw new ValidationException("It is required for the campaign to have an assigned owner");
        if (!(entity.getOwner() instanceof Advertiser))
            throw new ValidationException("Campaign owner has to be advertiser");
    }
}
