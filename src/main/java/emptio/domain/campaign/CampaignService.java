package emptio.domain.campaign;

import emptio.domain.Repository;
import emptio.domain.ValidationException;
import emptio.domain.Validator;

import java.util.Set;

public class CampaignService {

    Set<Validator<Campaign>> validators;
    Repository<Campaign> campaignRepository;

    public CampaignService(Set<Validator<Campaign>> validators) {
        this.validators = validators;
    }

    public Campaign newCampaign(
            String name, Placement placement, double pricePerInteraction, double totalBudget)
            throws ValidationException
    {
        Campaign campaign = new Campaign(name, placement, pricePerInteraction, totalBudget, 0.0, 0);

        try {
            validators.forEach(validator -> validator.validate(campaign));
        } catch (ValidationException e) {
            throw new ValidationException("Failed to create a campaign with given parameters, cause : " + e.getMessage());
        }

        return campaignRepository.find(
                campaignRepository.add(campaign)
        );
    }

}

