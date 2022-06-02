package tourGuide.api;

import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tourGuide.model.LocationExtended;
import tourGuide.model.VisitedLocationExtended;
import tourGuide.user.User;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


/*
 * Persistence in memory for this version with internal users.
 */

@Service
public class UserServiceImpl implements UserService {

    private static final boolean DEFAULT_LOCATION_HISTORY_ACTIVATED = true;
    private static final int DEFAULT_INTERNAL_USER_NUMBER = 100;
    private Random random;

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    private Map<String, User> internalUserMap;

    public UserServiceImpl(){
        this(true);
        logger.debug("new instance with empty constructor");
    }

    // empty instance of UserService (without any user) for test
    public UserServiceImpl(boolean randomInternalUsersMap) {
        logger.debug("new instance of UserService with randomInternalUsersMap = "+ randomInternalUsersMap);
        internalUserMap = new HashMap<>();
        random = new Random();
        if (randomInternalUsersMap){
            logger.debug("Initializing users");
            initializeInternalUsers(DEFAULT_INTERNAL_USER_NUMBER, DEFAULT_LOCATION_HISTORY_ACTIVATED);
            logger.debug("End of initialization");
        }
    }

/*
  Gets an user based on his name.
  @param userName to be searched for.
 * @return User found with the given name.
 */
    @Override
    public User getUser(String userName){
        logger.debug("getUser with userName = " + userName);
        return internalUserMap.get(userName);
    }


    public List<User> getAllUsers() {
        logger.debug("getAllUsers returns list of size = " + internalUserMap.size());
        return internalUserMap.values().stream().collect(Collectors.toList());
    }

/*
 * Replace the user list by the given user list. (for test)
 * @param userList to use for future user requests.
 */
    @Override
    public void setAllUsers(List<User> userList) {
        logger.debug("setAllUsers with list of size = " + userList.size());
        internalUserMap = new HashMap<>();
        userList.stream().forEach(user -> { internalUserMap.put(user.getUserName(), user); });
    }

    @Override
    public void addUser(User user) {
        logger.debug("addUser with userName = " + user.getUserName());
        if(!internalUserMap.containsKey(user.getUserName())) {
            internalUserMap.put(user.getUserName(), user);
        }
    }

    /*
     * Generate a user list .
     * @param expectedNumberOfUsers size of the list to be created.
     * @param withLocationHistory boolean to specify whether users shall be generated with or without a history of 3 visited locations.
     */

    @Override
    public  void  initializeInternalUsers(int expectedNumberOfUsers, boolean withLocationHistory){
        logger.debug("initializeInternalUsers with InternalUserNumber = " + expectedNumberOfUsers);
        internalUserMap = new HashMap<>();
        IntStream.range(0,expectedNumberOfUsers).forEach(i->{
            String userName = "internalUser" + i;
            String phone = "000" + i;
            String email = userName + "@tourGuide.com";
            User user = new User(UUID.randomUUID(), userName,phone, email);

            if(withLocationHistory){
                generateUserLocationHistory(user);
            }
            internalUserMap.put(userName, user);
        });
        logger.debug("End of initializeInternalUsers");
    }

    // helper method to populate the ecosystem
    private void generateUserLocationHistory(User user) {
        logger.debug("generateUserLocationHistory with userName = " + user.getUserName());
        IntStream.range(0, 3).forEach(i-> {
            user.addToVisitedLocations(new VisitedLocationExtended(user.getUserId(),
                    new LocationExtended(generateRandomLatitude(), generateRandomLongitude()), getRandomTime()));
        });
    }

    // helper method to populate the ecosystem
    private double generateRandomLongitude() {
        double leftLimit = -180;
        double rightLimit = 180;
        return leftLimit + random.nextDouble() * (rightLimit - leftLimit);
    }

    // helper method to populate the ecosystem
    private double generateRandomLatitude() {
        double leftLimit = -85.05112878;
        double rightLimit = 85.05112878;
        return leftLimit + random.nextDouble() * (rightLimit - leftLimit);
    }

    // helper method to populate the ecosystem
    private Date getRandomTime() {
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(new Random().nextInt(30));
        return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
    }

}
