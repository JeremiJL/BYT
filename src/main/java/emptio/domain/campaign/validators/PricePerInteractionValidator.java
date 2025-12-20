package emptio.domain.campaign.validators;

import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.campaign.Campaign;
import emptio.domain.common.Currency;

import java.math.BigDecimal;

public class PricePerInteractionValidator implements Validator<Campaign> {

    public final BigDecimal minPricePerInteraction = BigDecimal.ONE;

    @Override
    public void validate(Campaign entity) throws ValidationException {
        if (entity.getPricePerInteraction() == null)
            throw new ValidationException("Price per interaction can't be null.");
        if (entity.getPricePerInteraction().getValue() == null)
            throw new ValidationException("Price per interaction value can't be nul");
        if (entity.getPricePerInteraction().getCurrency() != Currency.EUR)
            throw new ValidationException("Price per interaction can be only expressed in EUR.");
        if (entity.getPricePerInteraction().getValue().compareTo(minPricePerInteraction) < 0)
            throw new ValidationException("Price per interaction can value can't be smaller than " + minPricePerInteraction + ".");
    }
}
