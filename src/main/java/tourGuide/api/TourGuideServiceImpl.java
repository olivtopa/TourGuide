package tourGuide.api;

import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tourGuide.model.*;
import tourGuide.user.User;
import tourGuide.user.UserReward;
import tripPricer.Provider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TourGuideServiceImpl implements TourGuideService {

    private Logger logger = LoggerFactory.getLogger(TourGuideServiceImpl.class);
    @Autowired
    private GpsRequestService gpsRequest;
    @Autowired
    private RewardRequestService rewardRequest;
    @Autowired
    private TripRequestService tripRequest;
    @Autowired
    private UserService userService;



    public TourGuideServiceImpl() {
    }

    //User's Reward list:
    @Override
    public List<UserReward> getUserRewards(User user) {
        logger.debug("getUserRewards userName = " + user.getUserName());
        return user.getUserRewards();
    }


    // The last user's location visited. (By visited location history).
    @Override
    public VisitedLocationExtended getLastUserLocation(User user) {
        logger.debug("getLastUserLocation with userName = " + user.getUserName());
        if (user.getVisitedLocations().size() > 0) {
            return user.getLastVisitedLocation();
        }
        return gpsRequest.getCurrentUserLocation(user);
    }

    // The last location visited by all users. (By visited location history).
    @Override
    public Map<String, LocationExtended> getLastLocationAllUsers() {
        logger.debug("getLastLocationAllUsers");
        // Get all users
        List<User> allUsers = userService.getAllUsers();
        Map<String, LocationExtended> allUserLocationsMap = new HashMap<String, LocationExtended>();
        // Get visited locations for all of them
        allUsers.forEach(user -> {
            allUserLocationsMap.put(user.getUserId().toString(), getLastUserLocation(user).location);

        });
        return allUserLocationsMap;
    }


    // proposition trip for a user. ( By user preferences).
    @Override
    public List<ProviderExtended> getTripDeals(User user) {
        logger.debug("getTripDeals userName = " + user.getUserName());
        // Calculate the sum of all reward points for given user
        int cumulativeRewardPoints = rewardRequest.sumOfAllRewardPoints(user);
        // List attractions in the neighborhood of the user
        List<AttractionNearBy> attractions = getNearByAttractions(user.getUserName());
        // Calculate trip proposals matching attractions list, user preferences and reward points
        return tripRequest.calculateProposals(user, attractions, cumulativeRewardPoints);
    }

    //5 attractions which are the closest to the user's location.
    @Override
    public List<AttractionNearBy> getNearByAttractions(String userName) {
        logger.debug("getNearByAttractions userName = " + userName);

        //distance from user location:
        User user = userService.getUser(userName);
        VisitedLocationExtended visitedLocation = getLastUserLocation(user);
        LocationExtended fromLocation = visitedLocation.location;

        //Full list of attractions
        List<AttractionDistance> fullList = new ArrayList<>();
        for (AttractionExtended toAttraction : gpsRequest.getAllAttractions()) {
            AttractionDistance attractionDistance = new AttractionDistance(fromLocation,toAttraction);
            fullList.add(attractionDistance);
        }

        // Sort list
        fullList.sort(null);
        List<AttractionNearBy>nearByAttractions = new ArrayList<>();
        for (int i=0; i<NUMBER_OF_PROPOSED_ATTRACTIONS && i<fullList.size(); i++ ){
            AttractionExtended attraction = fullList.get(i);
            int rewardPoints = rewardRequest.getRewardPoints(attraction, user);
            AttractionNearBy nearByAttraction = new AttractionNearBy(attraction, user, rewardPoints);
            nearByAttractions.add(nearByAttraction);
        }
        return nearByAttractions;
        }
    }




