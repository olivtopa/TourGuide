package tourGuide;

import gpsUtil.GpsUtil;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import rewardCentral.RewardCentral;
import tourGuide.helper.InternalTestHelper;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.user.UserPreferences;

import static org.junit.Assert.*;

public class TestUpdatePreferences {



    @Test
    public void updatePreferences() {
        GpsUtil gpsUtil = new GpsUtil();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
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
        assertEquals(4, tourGuideService.getUser(userName).getUserPreferences().getNumberOfChildren());;



    }
}
