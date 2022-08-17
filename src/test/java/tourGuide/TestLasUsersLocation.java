package tourGuide;

import gpsUtil.GpsUtil;
import gpsUtil.location.VisitedLocation;
import org.junit.Assert;
import org.junit.Test;
import rewardCentral.RewardCentral;
import tourGuide.helper.InternalTestHelper;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;

import java.util.List;

public class TestLasUsersLocation {

    @Test
   public void isListOfLocationEmpty(){

        //Given
        GpsUtil gpsUtil = new GpsUtil();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
        InternalTestHelper.setInternalUserNumber(5);
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);

        //When
        List<VisitedLocation> resultat = tourGuideService.LastUsersLocation();
        System.out.println(resultat);

        //Then
        Assert.assertEquals(5,resultat.size());

    }
}
