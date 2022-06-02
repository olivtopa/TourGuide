package tourGuide.api;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import tourGuide.user.User;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Test
    public void addAUser() {
        // GIVEN
        UserService userService = new UserServiceImpl(false);
        User givenUser = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
        // WHEN
        userService.addUser(givenUser);
        User resultUser = userService.getUser(givenUser.getUserName());
        // THEN
        assertNotNull(resultUser);
        assertEquals(givenUser.getUserId(), resultUser.getUserId());
    }

    @Test
    public void checkRightUserInAList() {
        // GIVEN
        UserService userService = new UserServiceImpl(false);
        User user1 = new User(UUID.randomUUID(), "jon1", "0001", "jon1@tourGuide.com");
        User user2 = new User(UUID.randomUUID(), "jon2", "0002", "jon2@tourGuide.com");
        userService.addUser(user1);
        userService.addUser(user2);
        // WHEN
        User user = userService.getUser(user2.getUserName());
        // THEN
        assertEquals(user2.getUserId(), user.getUserId());
        assertEquals(user2.getEmailAddress(), user.getEmailAddress());
    }
}
