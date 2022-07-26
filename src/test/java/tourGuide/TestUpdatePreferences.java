package tourGuide;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import tourGuide.helper.InternalTestHelper;
import tourGuide.service.TourGuideService;
import tourGuide.user.UserPreferences;

import static org.junit.Assert.*;

public class TestUpdatePreferences {

    @Autowired TourGuideController tourGuideController;
    @Autowired TourGuideService tourGuideService;

    @Test
    public void updatePreferences() {

        //GIVEN
        InternalTestHelper.setInternalUserNumber(0);
        UserPreferences newPreferences = new UserPreferences();
        newPreferences.setNumberOfChildren(4);
        String userName = "internalUser1";

        //WHEN
        tourGuideService.getUser(userName).setUserPreferences(newPreferences);

        assertEquals(4, tourGuideService.getUser(userName).getUserPreferences().getNumberOfChildren());;



    }
}
