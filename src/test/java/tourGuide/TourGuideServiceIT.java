package tourGuide;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tourGuide.NewTripPricer.Provider;
import tourGuide.helper.InternalTestHelper;
import tourGuide.newGpsUtil.VisitedLocation;
import tourGuide.service.*;
import tourGuide.user.User;


import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.*;

@SpringBootTest
public class TourGuideServiceIT {

    @Test
    public void getUserLocation() throws ExecutionException, InterruptedException {
        //GIVEN
        GpsUtilService gpsUtil = new GpsUtilService();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentralService());
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);

        User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

        //WHEN
        CompletableFuture<VisitedLocation> visitedLocation = tourGuideService.trackUserLocation(user);
        tourGuideService.tracker.stopTracking();

        //THEN
        assertTrue(visitedLocation.join().getUserId().equals(user.getUserId()));
    }




    @Test
    public void trackUser() throws ExecutionException, InterruptedException {

        //GIVEN
        GpsUtilService gpsUtil = new GpsUtilService();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentralService());
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
        TripDealService tripDealService= new TripDealService(new TripPricerService());
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentralService());
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
        InternalTestHelper.setInternalUserNumber(0);
        String userName = "internalUser1";
        tourGuideService.getUser(userName).getUserPreferences().setNumberOfChildren(2);
        int nbOfChildren = tourGuideService.getUser(userName).getUserPreferences().getNumberOfChildren();

        //WHEN
        List<Provider> listOfProvider = tripDealService.getTripDeals(tourGuideService.getUser(userName));

        //THEN
        assertNotNull(listOfProvider);
        assertFalse(listOfProvider.isEmpty());

    }


}
