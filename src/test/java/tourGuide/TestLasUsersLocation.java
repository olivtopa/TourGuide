package tourGuide;


;
import org.junit.Assert;
import org.junit.Test;
import rewardCentral.RewardCentral;
import tourGuide.helper.InternalTestHelper;
import tourGuide.newGpsUtil.Location;
import tourGuide.service.GpsUtilService;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;

import java.util.Map;

public class TestLasUsersLocation {

    @Test
    public void isListOfLocationEmpty() {

        //Given
        GpsUtilService gpsUtil = new GpsUtilService();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
        InternalTestHelper.setInternalUserNumber(5);
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);

        //When
        Map<String, Location> resultat = tourGuideService.lastUsersLocation();
        System.out.println(resultat);

        //Then
        Assert.assertEquals(5, resultat.size());
    }
}
