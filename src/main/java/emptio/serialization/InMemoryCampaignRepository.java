package emptio.serialization;

import emptio.domain.Repository;
import emptio.domain.campaign.Campaign;

import java.util.HashMap;
import java.util.Map;

public class InMemoryCampaignRepository implements Repository<Campaign> {

    private final Map<Integer, Campaign> campaigns = new HashMap<>();

    @Override
    public Campaign add(Campaign campaign) {
        campaigns.put(campaign.getId(), campaign);
        return campaign;
    }

    @Override
    public Campaign find(Integer campaignId) {
        return campaigns.get(campaignId);
    }
}