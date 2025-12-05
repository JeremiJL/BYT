package emptio.domain.campaign;

import emptio.domain.Repository;
import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.common.Cost;
import emptio.domain.common.Currency;

import java.math.BigDecimal;
import java.util.Set;

public class CampaignService {

    static Set<Validator<Campaign>> validators;
    static Repository<Campaign> campaignRepository;

    public CampaignService(Set<Validator<Campaign>> validators, Repository<Campaign> campaignRepository) {
        CampaignService.validators = validators;
        CampaignService.campaignRepository = campaignRepository;
    }

    public Campaign newCampaign(
            String name, Placement placement, BigDecimal pricePerInteraction, BigDecimal totalBudget)
            throws ValidationException
    {
        Campaign campaign = new Campaign(
                Campaign.idService.getNewId(),
                name, placement,
                new Cost(pricePerInteraction, Currency.EUR),
                new Cost(totalBudget, Currency.EUR), new Cost(BigDecimal.ZERO, Currency.EUR), 0);

        return campaignRepository.find(
                campaignRepository.add(
                        validate(campaign)
                )
        );
    }

    public void recordInteraction(int id) {
        Campaign oldCampaign = campaignRepository.find(id);
        Campaign updatedCampaign = oldCampaign.
                withInteractionsCount(oldCampaign.getInteractionsCount() + 1)
                .withBudgetSpent(Cost.add(oldCampaign.getBudgetSpent(), oldCampaign.getPricePerInteraction()));
        campaignRepository.update(
                validate(updatedCampaign)
        );
    }

    private Campaign validate(Campaign campaignToValidate) {
        try {
            validators.forEach(validator -> validator.validate(campaignToValidate));
        } catch (ValidationException e) {
            throw new ValidationException("Failed to create a campaign with given parameters, cause : " + e.getMessage());
        }
        return campaignToValidate;
    }
}



