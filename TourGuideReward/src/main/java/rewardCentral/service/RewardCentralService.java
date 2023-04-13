package rewardCentral.service;

import org.springframework.stereotype.Service;
import rewardCentral.RewardCentral;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;


@Service
public class RewardCentralService {

    private int attractionRewardPoints;
        public int getAttractionRewardPoints(UUID attractionId, UUID userId) {
            RewardCentral rewardCentral = new RewardCentral();
            rewardCentral.getAttractionRewardPoints(attractionId, userId);
            return attractionRewardPoints;
    }
}
