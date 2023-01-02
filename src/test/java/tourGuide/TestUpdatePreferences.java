package tourGuide;


import org.junit.Test;
import tourGuide.helper.InternalTestHelper;
import tourGuide.newRewardCentral.RewardCentral;
import tourGuide.service.GpsUtilService;
import tourGuide.service.RewardCentralService;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.user.UserPreferences;

import static org.junit.Assert.assertEquals;

public class TestUpdatePreferences {


    @Test
    public void updatePreferences() {
        GpsUtilService gpsUtil = new GpsUtilService();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentralService());
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);

        //GIVEN
        InternalTestHelper.setInternalUserNumber(0);
        UserPreferences newPreferences = new UserPreferences();
        newPreferences.setNumberOfChildren(4);
        //newPreferences.setLowerPricePoint();
        String userName = "internalUser1";

        //WHEN
        tourGuideService.getUser(userName).setUserPreferences(newPreferences);

        //THEN
        assertEquals(4, tourGuideService.getUser(userName).getUserPreferences().getNumberOfChildren());
        ;


    }
}
