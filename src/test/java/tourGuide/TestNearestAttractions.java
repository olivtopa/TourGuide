package tourGuide;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import org.junit.Assert;
import org.junit.Test;
import rewardCentral.RewardCentral;
import tourGuide.helper.InternalTestHelper;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;

import java.util.List;
import java.util.Map;

public class TestNearestAttractions {

    @Test
    public void the5nearestAtttractions(){

        //Given
        GpsUtil gpsUtil = new GpsUtil();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
        InternalTestHelper.setInternalUserNumber(0);
        String userName = "internalUser1";

        //When
        Map<Attraction,Double> resultat = tourGuideService.the5NearestAttractions("internalUser1");

        //Then
        Assert.assertNull(resultat.get(5));

    }

}
