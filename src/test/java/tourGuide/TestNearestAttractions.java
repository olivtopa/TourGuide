package tourGuide;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rewardCentral.RewardCentral;
import tourGuide.helper.InternalTestHelper;
import tourGuide.service.GpsUtilService;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;

import java.util.List;
import java.util.Map;

public class TestNearestAttractions {
    @Autowired
    GpsUtilService gpsUtil;

    @Test
    public void the5nearestAtttractions() {

        //Given
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
        InternalTestHelper.setInternalUserNumber(0);
        String userName = "internalUser1";

        //When
        List<OutputAttraction> resultat = tourGuideService.the5NearestAttractions("internalUser1");
        System.out.println(resultat);

        //Then
        Assert.assertNotNull(resultat);
        Assert.assertEquals(5, resultat.size());


    }

}
