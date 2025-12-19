package emptio.builder;

import emptio.domain.campaign.Campaign;
import emptio.domain.campaign.CampaignService;
import emptio.domain.campaign.Placement;
import emptio.domain.user.AccountType;
import emptio.domain.user.Advertiser;
import lombok.Setter;

import java.math.BigDecimal;

@Setter
public class CampaignBuilder {

    private final CampaignService campaignService;

    private Advertiser owner;
    private String name;
    private Placement placement;
    private BigDecimal pricePerInteraction;
    private BigDecimal totalBudget;

    public CampaignBuilder(CampaignService campaignService, UserBuilder userBuilder) {
        this.campaignService = campaignService;
        this.owner = userBuilder.build(AccountType.ADVERTISER);
        this.name = "Campaign";
        this.placement = Placement.LISTING;
        this.pricePerInteraction = BigDecimal.TWO;
        this.totalBudget = BigDecimal.valueOf(100);
    }

    public Campaign build() {
        return campaignService.newCampaign(owner, name, placement, pricePerInteraction, totalBudget);
    }

}
