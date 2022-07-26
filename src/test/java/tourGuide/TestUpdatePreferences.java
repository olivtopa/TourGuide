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

    @Autowired TourGuideController tourGuideController;

    @Test
    public void updatePreferences() {

        //GIVEN
        GpsUtil gpsUtil = new GpsUtil();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
        InternalTestHelper.setInternalUserNumber(0);
        UserPreferences newPreferences = new UserPreferences();
        newPreferences.setNumberOfChildren(4);
        String userName = "internalUser1";

        //WHEN
        tourGuideController.updateUserPreferences(newPreferences,userName);

        assertEquals(4,newPreferences.getNumberOfChildren());



    }
}
