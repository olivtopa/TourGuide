package reward;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import rewardCentral.RewardCentral;
import tourGuide.api.GpsRequestService;
import tourGuide.model.AttractionExtended;
import tourGuide.model.LocationExtended;
import tourGuide.model.VisitedLocationExtended;
import tourGuide.user.User;
import tourGuide.user.UserReward;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

@Service
public class RewardServiceImpl implements RewardService{

    private static final int NUMBER_OF_EXPECTED_USER_PARTITIONS = 100;
    private static final int THREAD_POOL_SIZE = NUMBER_OF_EXPECTED_USER_PARTITIONS * 2;

    private static final int DEFAULT_PROXIMITY_MAXIMAL_DISTANCE = 10;
    private int proximityMaximalDistance = DEFAULT_PROXIMITY_MAXIMAL_DISTANCE;

    private GpsRequestService gpsUtil;

    private Logger logger = LoggerFactory.getLogger(RewardServiceImpl.class);

    @Autowired
    private RewardCentral rewardCentral;


/* Set Maximal distance */
    @Override
    public void setProximityMaximalDistance(int proximityBuffer) {
        this.proximityMaximalDistance = proximityBuffer;
    }

    /* Get Maximal distance */
    @Override
    public int getProximityMaximalDistance() {
        return this.proximityMaximalDistance;
    }

    /* is the visitedLocation close to an attraction? */
    @Override
    public boolean nearAttraction(VisitedLocationExtended visitedLocation,  AttractionExtended attractionExtended) {
        logger.debug("nearAttraction " + attractionExtended.name);
        LocationExtended attractionLocation = new LocationExtended(attractionExtended.latitude, attractionExtended.longitude);
        if (attractionLocation.getDistance(visitedLocation.location) > proximityMaximalDistance) {
            return false;
        }
        return true;
    }

    @Override
    public int getRewardPoints(AttractionExtended attractionExtended, User user) {
        logger.debug("getRewardPoints userName = " + user.getUserName() + " for attraction " + attractionExtended.name );
        int points = rewardCentral.getAttractionRewardPoints(attractionExtended.id, user.getUserId());
        return points;
    }

    @Override
    public void addAllNewRewards(User user, List<AttractionExtended> attractions)	{
        logger.debug("addAllNewRewards userName = " + user.getUserName()
                + " and attractionList of size " + attractions.size());
        for(VisitedLocationExtended visitedLocation : user.getVisitedLocations()) {
            for(AttractionExtended attractionData : attractions) {
                long numberOfRewardsOfTheUserForThisAttraction =
                        user.getUserRewards().stream().filter(reward ->
                                reward.attraction.name.equals(attractionData.name)).count();
                if( numberOfRewardsOfTheUserForThisAttraction == 0) {
                    if(nearAttraction(visitedLocation, attractionData)) {
                        logger.debug("addAllNewRewards new Reward for userName = " + user.getUserName() + " for attraction " + attractionData.name );
                        user.addUserReward(new UserReward(visitedLocation, attractionData, getRewardPoints(attractionData, user)));
                    }
                }
            }
        }
    }

    private List<List<User>> divideUserList(List<User> userList) {
        List<List<User>> partitionList = new LinkedList<List<User>>();
        int expectedSize = userList.size() / NUMBER_OF_EXPECTED_USER_PARTITIONS;
        if (expectedSize == 0) {
            partitionList.add(userList);
            return partitionList;
        }
        for (int i = 0; i < userList.size(); i += expectedSize) {
            partitionList.add(userList.subList(i, Math.min(i + expectedSize, userList.size())));
        }
        return partitionList;
    }

    @Override
    public List<User> addAllNewRewardsAllUsers(List<User> userList, List<AttractionExtended> attractions)	{
        logger.debug("addAllNewRewardsAllUsers userListName of size = " + userList.size()
                + " and attractionList of size " + attractions.size());
        // The number of threads has been defined after several tests to match the performance target
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_POOL_SIZE);
        // Divide user list into several parts and submit work separately for these parts
        divideUserList(userList).stream().parallel().forEach( partition -> {
            try {
                logger.debug("addAllNewRewardsAllUsers submits calculation for user partition of size" +  partition.size());
                forkJoinPool.submit( () -> partition.stream().parallel().forEach(user -> {
                    addAllNewRewards(user, attractions);
                })).get();
            } catch (InterruptedException | ExecutionException e) {
                logger.error("addAllNewRewardsAllUsers got an exception");
                e.printStackTrace();
                throw new RuntimeException("addAllNewRewardsAllUsers got an exception");
            }
        });
        forkJoinPool.shutdown();
        return userList;
    }

    @Override
    public int sumOfAllRewardPoints(User user) {
        logger.debug("sumOfAllRewardPoints userName = " + user.getUserName()) ;
        int cumulativeRewardPoints = user.getUserRewards().stream().mapToInt(i -> i.getRewardPoints()).sum();
        return cumulativeRewardPoints;
    }


}


