package tourGuide;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import rewardCentral.RewardCentral;
import tourGuide.helper.InternalTestHelper;
import tourGuide.newGpsUtil.VisitedLocation;
import tourGuide.service.GpsUtilService;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.user.User;
import tripPricer.Provider;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

@SpringBootTest
public class TestTourGuideService {

    @Test
    public void getUserLocation() throws ExecutionException, InterruptedException {
        //GIVEN
        GpsUtilService gpsUtil = new GpsUtilService();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);

        User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

        //WHEN
        CompletableFuture<VisitedLocation> visitedLocation = tourGuideService.trackUserLocation(user);
        tourGuideService.tracker.stopTracking();

        //THEN
        assertTrue(visitedLocation.join().getUserId().equals(user.getUserId()));
    }

    @Test
    public void addUser() throws ExecutionException, InterruptedException {

        //GIVEN
        GpsUtilService gpsUtil = new GpsUtilService();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);

        User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
        User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");

        tourGuideService.addUser(user);
        tourGuideService.addUser(user2);

        //WHEN
        User retrivedUser = tourGuideService.getUser(user.getUserName());
        User retrivedUser2 = tourGuideService.getUser(user2.getUserName());

        tourGuideService.tracker.stopTracking();

        //THEN
        assertEquals(user, retrivedUser);
        assertEquals(user2, retrivedUser2);
    }

    @Test
    public void getAllUsers() throws ExecutionException, InterruptedException {

        //GIVEN
        GpsUtilService gpsUtil = new GpsUtilService();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
        InternalTestHelper.setInternalUserNumber(0);
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);

        User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
        User user2 = new User(UUID.randomUUID(), "jon2", "000", "jon2@tourGuide.com");

        tourGuideService.addUser(user);
        tourGuideService.addUser(user2);

        //WHEN
        List<User> allUsers = tourGuideService.getAllUsers();

        tourGuideService.tracker.stopTracking();

        //THEN
        assertTrue(allUsers.contains(user));
        assertTrue(allUsers.contains(user2));
    }

    @Test
    public void trackUser() throws ExecutionException, InterruptedException {

        //GIVEN
        GpsUtilService gpsUtil = new GpsUtilService();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
        InternalTestHelper.setInternalUserNumber(0);
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);

        User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

        //WHEN
        CompletableFuture<VisitedLocation> visitedLocation = tourGuideService.trackUserLocation(user);

        tourGuideService.tracker.stopTracking();

        //THEN
        assertEquals(user.getUserId(), visitedLocation.join().getUserId());
    }


    @Test
    public void getTripDeals() {
        //GIVEN
        GpsUtilService gpsUtil = new GpsUtilService();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
        InternalTestHelper.setInternalUserNumber(0);
        String userName = "internalUser1";
        tourGuideService.getUser(userName).getUserPreferences().setNumberOfChildren(2);
        int nbOfChildren = tourGuideService.getUser(userName).getUserPreferences().getNumberOfChildren();

        //WHEN
        List<Provider> listOfProvider = tourGuideService.getTripDeals(tourGuideService.getUser(userName));

        //THEN
        assertNotNull(listOfProvider);
        assertFalse(listOfProvider.isEmpty());

    }


}
