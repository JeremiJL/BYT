package emptio.domain.campaign.validators;

import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.campaign.Campaign;
import emptio.domain.common.Currency;

import java.math.BigDecimal;

public class BudgetSpentValidator implements Validator<Campaign> {

    @Override
    public void validate(Campaign entity) throws ValidationException {
        if (entity.getBudgetSpent() == null)
            throw new ValidationException("Budget spent can't be null");
        if (entity.getBudgetSpent().value() == null)
            throw new ValidationException("Budget spent value can't be null");
        if (entity.getBudgetSpent().currency() != Currency.EUR)
            throw new ValidationException("Budget spent can be only expressed in EUR.");
        if (entity.getBudgetSpent().value().compareTo(BigDecimal.ZERO) < 0)
            throw new ValidationException("Budget spent can't be smaller than 0.");
        if (entity.getBudgetSpent().value().compareTo(entity.getTotalBudget().value()) > 0)
            throw new ValidationException("Budget spent can't be greater than total budget.");
    }
}
