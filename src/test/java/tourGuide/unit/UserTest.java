package tourGuide.unit;


import org.junit.Test;
import tourGuide.helper.InternalTestHelper;
import tourGuide.user.User;
import tourGuide.user.UserPreferences;

import java.util.UUID;

import static org.junit.Assert.assertEquals;

public class UserTest {

    @Test
    public void updatePreferences() {

        //GIVEN
        InternalTestHelper.setInternalUserNumber(0);
        UserPreferences newPreferences = new UserPreferences();
        newPreferences.setNumberOfChildren(4);

        User user = new User(UUID.randomUUID(), "", "", "");

        //WHEN
        user.setUserPreferences(newPreferences);

        //THEN
        assertEquals(4, user.getUserPreferences().getNumberOfChildren());
    }
}
