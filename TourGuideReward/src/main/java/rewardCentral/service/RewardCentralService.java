package rewardCentral.service;

import org.springframework.stereotype.Service;
import rewardCentral.RewardCentral;

import java.util.UUID;

@Service
public class RewardCentralService {

    public int getAttractionRewardPoints(UUID attractionId, UUID userId) {
            RewardCentral rewardCentral = new RewardCentral();
            return rewardCentral.getAttractionRewardPoints(attractionId, userId);
    }
}
