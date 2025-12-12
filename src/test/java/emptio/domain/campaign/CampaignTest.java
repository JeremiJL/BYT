package emptio.domain.campaign;

import emptio.builder.AddressBuilder;
import emptio.builder.CampaignBuilder;
import emptio.builder.UserBuilder;
import emptio.domain.Repository;
import emptio.domain.ValidationException;
import emptio.domain.Validator;
import emptio.domain.campaign.validators.*;
import emptio.domain.user.UserService;
import emptio.serialization.InMemoryCredentialsRepository;
import emptio.serialization.InMemoryRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class CampaignTest {

    CampaignService campaignService;
    Repository<Campaign> campaignRepository;
    Set<Validator<Campaign>> validators;
    CampaignBuilder campaignBuilder;
    UserBuilder userBuilder;
    UserService userService;
    AddressBuilder addressBuilder;

    @BeforeEach
    void setUp() {
        campaignRepository = new InMemoryRepository<>();
        validators = new HashSet<>();
        campaignService = new CampaignService(validators,campaignRepository);
        addressBuilder = new AddressBuilder();
        userService = new UserService(new InMemoryRepository<>(), new InMemoryCredentialsRepository(), new HashSet<>());
        userBuilder = new UserBuilder(userService, addressBuilder);
        campaignBuilder = new CampaignBuilder(campaignService, userBuilder);
    }

    @Test
    void shouldValidateName() {
        NameValidator nameValidator = new NameValidator();
        validators.add(nameValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(NullPointerException.class, () -> {
            campaignBuilder.setName(null);
            campaignBuilder.build();
        });
        assertThrows(ValidationException.class, () -> {
            campaignBuilder.setName("");
            campaignBuilder.build();
        });
        assertThrows(ValidationException.class, () -> {
            campaignBuilder.setName(" ");
            campaignBuilder.build();
        });
        assertThrows(ValidationException.class, () -> {
            campaignBuilder.setName(stringOfGivenLength(nameValidator.maxCharacters + 1));
            campaignBuilder.build();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        campaignBuilder.setName("My winter campaign");
        campaignBuilder.build();
    }

    @Test
    void shouldValidatePlacement() {
        PlacementValidator placementValidator = new PlacementValidator();
        validators.add(placementValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(NullPointerException.class, () -> {
            campaignBuilder.setPlacement(null);
            campaignBuilder.build();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        campaignBuilder.setPlacement(Placement.LISTING);
        campaignBuilder.build();
    }

    @Test
    void shouldValidateTotalBudget() {
        TotalBudgetValidator totalBudgetValidator = new TotalBudgetValidator();
        validators.add(totalBudgetValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(NullPointerException.class, () -> {
            campaignBuilder.setTotalBudget(null);
            campaignBuilder.build();
        });
        assertThrows(ValidationException.class, () -> {
            campaignBuilder.setTotalBudget(totalBudgetValidator.minCampaignBudget.subtract(BigDecimal.ONE));
            campaignBuilder.build();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        campaignBuilder.setTotalBudget(totalBudgetValidator.minCampaignBudget.add(BigDecimal.ONE));
        campaignBuilder.build();
    }

    @Test
    void shouldValidatePricePerInteraction() {
        PricePerInteractionValidator pricePerInteractionValidator = new PricePerInteractionValidator();
        validators.add(pricePerInteractionValidator);
        // Assert potential negative cases - validation fails - exception is thrown
        assertThrows(NullPointerException.class, () -> {
            campaignBuilder.setPricePerInteraction(null);
            campaignBuilder.build();
        });
        assertThrows(ValidationException.class, () -> {
            campaignBuilder.setPricePerInteraction(pricePerInteractionValidator.minPricePerInteraction.subtract(BigDecimal.valueOf(0.1)));
            campaignBuilder.build();
        });
        // Assert potential positive cases - validation succeeds - exception is not thrown
        campaignBuilder.setPricePerInteraction(pricePerInteractionValidator.minPricePerInteraction.add(BigDecimal.ONE));
        campaignBuilder.build();
    }

    @Test
    void shouldValidateRecordingInteractions() {
         campaignBuilder.setTotalBudget(BigDecimal.valueOf(100));
         campaignBuilder.setPricePerInteraction(BigDecimal.TEN);

        Campaign myCampaign = campaignBuilder.build();
        Collections.addAll(validators, new BudgetSpentValidator(), new InteractionsCountValidator());

        // Interactions count and spent budget upon creation
        assertEquals(0, myCampaign.getInteractionsCount());
        assertEquals(BigDecimal.ZERO, myCampaign.getBudgetSpent().value());
        // Interactions count and spent budget after recording two interactions
        campaignService.recordInteraction(myCampaign.getId());
        campaignService.recordInteraction(myCampaign.getId());
        myCampaign = campaignRepository.find(myCampaign.getId());
        assertEquals(2, myCampaign.getInteractionsCount());
        assertEquals(BigDecimal.valueOf(20), myCampaign.getBudgetSpent().value());
    }

    private String stringOfGivenLength(int length) {
        return "-".repeat(length);
    }

}


