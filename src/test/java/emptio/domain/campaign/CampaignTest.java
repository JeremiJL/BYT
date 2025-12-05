package emptio.domain.campaign;

import emptio.domain.Repository;
import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.campaign.validators.*;
import emptio.serialization.InMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CampaignTest {

    Repository<Campaign> campaignRepository;
    Set<Validator<Campaign>> validators;

    CampaignService campaignService;
    CampaignBuilder campaignBuilder;

    @BeforeEach
    void setUp() {
        campaignRepository = new InMemoryRepository<>();
        validators = new HashSet<>();
        campaignBuilder = new CampaignBuilder();
        campaignService = new CampaignService(validators, campaignRepository);
    }

    @Test
    void shouldValidateName() {
        NameValidator nameValidator = new NameValidator();
        validators.add(nameValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            campaignBuilder.withName(null).newCampaign();
        });
        assertThrows(ValidationException.class, () -> {
            campaignBuilder.withName("").newCampaign();
        });
        assertThrows(ValidationException.class, () -> {
            campaignBuilder.withName(" ").newCampaign();
        });
        assertThrows(ValidationException.class, () -> {
            campaignBuilder.withName(stringOfGivenLength(nameValidator.maxCharacters + 1)).newCampaign();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        campaignBuilder.withName("My winter campaign").newCampaign();
    }

    @Test
    void shouldValidatePlacement() {
        PlacementValidator placementValidator = new PlacementValidator();
        validators.add(placementValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            campaignBuilder.withPlacement(null).newCampaign();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        campaignBuilder.withPlacement(Placement.LISTING).newCampaign();
    }

    @Test
    void shouldValidateTotalBudget() {
        TotalBudgetValidator totalBudgetValidator = new TotalBudgetValidator();
        validators.add(totalBudgetValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            campaignBuilder.withTotalBudget(null).newCampaign();
        });
        assertThrows(ValidationException.class, () -> {
            campaignBuilder.withTotalBudget(totalBudgetValidator.minCampaignBudget.subtract(BigDecimal.ONE)).newCampaign();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        campaignBuilder.withTotalBudget(totalBudgetValidator.minCampaignBudget.add(BigDecimal.ONE)).newCampaign();
    }

    @Test
    void shouldValidatePricePerInteraction() {
        PricePerInteractionValidator pricePerInteractionValidator = new PricePerInteractionValidator();
        validators.add(pricePerInteractionValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(ValidationException.class, () -> {
            campaignBuilder.withPricePerInteraction(null).newCampaign();
        });
        assertThrows(ValidationException.class, () -> {
            campaignBuilder.withPricePerInteraction(pricePerInteractionValidator.minPricePerInteraction.subtract(BigDecimal.valueOf(0.1))).newCampaign();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        campaignBuilder.withPricePerInteraction(pricePerInteractionValidator.minPricePerInteraction.add(BigDecimal.ONE)).newCampaign();
    }

    @Test
    void shouldValidateRecordingInteractions() {
        Campaign myCampaign = campaignBuilder.withTotalBudget(BigDecimal.valueOf(100)).withPricePerInteraction(BigDecimal.TEN).newCampaign();
        Collections.addAll(validators, new BudgetSpentValidator(), new InteractionsCountValidator());

        // Interactions count and spent budget upon creation
        assertEquals(0, myCampaign.interactionsCount);
        assertEquals(BigDecimal.ZERO, myCampaign.budgetSpent.value);
        // Interactions count and spent budget after recording two interactions
        campaignService.recordInteraction(myCampaign.getId());
        campaignService.recordInteraction(myCampaign.getId());
        myCampaign = campaignRepository.find(myCampaign.getId());
        assertEquals(2, myCampaign.interactionsCount);
        assertEquals(BigDecimal.valueOf(20), myCampaign.budgetSpent.value);
    }


    private class CampaignBuilder {

        private String name;
        private Placement placement;
        private BigDecimal pricePerInteraction;
        private BigDecimal totalBudget;

        Campaign newCampaign() {
            return campaignService.newCampaign(name, placement, pricePerInteraction, totalBudget);
        }

        CampaignBuilder withName(String name) {
            this.name = name;
            return this;
        }

        CampaignBuilder withPlacement(Placement name) {
            this.placement = name;
            return this;
        }

        CampaignBuilder withPricePerInteraction(BigDecimal pricePerInteraction) {
            this.pricePerInteraction = pricePerInteraction;
            return this;
        }

        CampaignBuilder withTotalBudget(BigDecimal totalBudget) {
            this.totalBudget = totalBudget;
            return this;
        }
    }

    private String stringOfGivenLength(int length) {
        return "-".repeat(length);
    }

}


