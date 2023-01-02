package tourGuide;

import org.junit.Test;

import tourGuide.helper.InternalTestHelper;
import tourGuide.newGpsUtil.Attraction;
import tourGuide.newGpsUtil.VisitedLocation;
import tourGuide.newRewardCentral.RewardCentral;
import tourGuide.service.GpsUtilService;
import tourGuide.service.RewardCentralService;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.user.User;
import tourGuide.user.UserReward;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class TestRewardsService {

    @Test
    public void userGetRewards() throws ExecutionException, InterruptedException {
        GpsUtilService gpsUtil = new GpsUtilService();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentralService());

        InternalTestHelper.setInternalUserNumber(0);
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);

        User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
        Attraction attraction = gpsUtil.getAttractions().get(0);
        user.addToVisitedLocations(new VisitedLocation());
        tourGuideService.trackUserLocation(user);
        List<UserReward> userRewards = user.getUserRewards();
        tourGuideService.tracker.stopTracking();
        assertTrue(userRewards.size() == 1);
    }

    @Test
    public void isWithinAttractionProximity() {
        GpsUtilService gpsUtil = new GpsUtilService();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentralService());
        Attraction attraction = gpsUtil.getAttractions().get(0);
        assertTrue(rewardsService.isWithinAttractionProximity(attraction, attraction.getLocation()));
    }

    //@Ignore // Needs fixed - can throw ConcurrentModificationException
    @Test
    public void nearAllAttractions() throws ExecutionException, InterruptedException {
        GpsUtilService gpsUtil = new GpsUtilService();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentralService());
        rewardsService.setProximityBuffer(Integer.MAX_VALUE);

        InternalTestHelper.setInternalUserNumber(1);
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);

        rewardsService.calculateRewards(tourGuideService.getAllUsers().get(0));
        List<UserReward> userRewards = tourGuideService.getUserRewards(tourGuideService.getAllUsers().get(0));
        tourGuideService.tracker.stopTracking();

        assertEquals(gpsUtil.getAttractions().size(), userRewards.size());
    }

}
