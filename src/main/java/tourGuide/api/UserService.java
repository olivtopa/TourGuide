package tourGuide.api;


import tourGuide.user.User;

import java.util.List;

/**
 * Interface for user services
 * Its implementation is in tourGuide.api.UserServiceImpl.
 */
public interface UserService {

    User getUser(String userName);

    List<User> getAllUsers();

    void setAllUsers(List<User> userList);

    void addUser(User user);

    void initializeInternalUsers(int expectedNumberOfUsers, boolean withLocationHistory);
}
