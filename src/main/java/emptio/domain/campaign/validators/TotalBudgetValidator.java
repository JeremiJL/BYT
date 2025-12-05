package emptio.domain.campaign.validators;

import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.campaign.Campaign;
import emptio.domain.common.Currency;

import java.math.BigDecimal;

public class TotalBudgetValidator implements Validator<Campaign> {

    public final BigDecimal minCampaignBudget = BigDecimal.valueOf(10);

    @Override
    public void validate(Campaign entity) throws ValidationException {
        if (entity.totalBudget == null)
            throw new ValidationException("Total budget can't be null");
        if (entity.totalBudget.value == null)
            throw new ValidationException("Total budget value can't be null");
        if (entity.totalBudget.currency != Currency.EUR)
            throw new ValidationException("Total budget can be only expressed in EUR.");
        if (entity.totalBudget.value.compareTo(minCampaignBudget) < 0)
            throw new ValidationException("Campaign's budget can't be smaller than " + minCampaignBudget + ".");
    }
}

