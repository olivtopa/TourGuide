package tourGuide.integration;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tourGuide.OutputAttraction;
import tourGuide.helper.InternalTestHelper;
import tourGuide.newGpsUtil.Attraction;
import tourGuide.newGpsUtil.Location;
import tourGuide.newGpsUtil.VisitedLocation;
import tourGuide.service.GpsUtilService;
import tourGuide.service.RewardCentralService;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.user.User;

import java.util.List;
import java.util.Map;
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
    public void the5nearestAttractions() {

        //Given
        GpsUtilService gpsUtil = new GpsUtilService();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentralService());
        InternalTestHelper.setInternalUserNumber(1);
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
        tourGuideService.getUser("internalUser0");

        //When
        List<OutputAttraction> resultat = tourGuideService.the5NearestAttractions("internalUser0");
        System.out.println(resultat);

        //Then
        Assert.assertNotNull(resultat);
        Assert.assertEquals(5, resultat.size());
    }

    @Test
    public void isListOfLocationEmpty() {

        //Given
        GpsUtilService gpsUtil = new GpsUtilService();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentralService());
        InternalTestHelper.setInternalUserNumber(5);
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);

        //When
        Map<String, Location> result = tourGuideService.lastUsersLocation();
        System.out.println(result);

        //Then
        Assert.assertEquals(5, result.size());
    }

    @Test
    public void getNearbyAttractions() {

        //GIVEN
        GpsUtilService gpsUtil = new GpsUtilService();

        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentralService());

        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);

        UUID userId = UUID.randomUUID();
        VisitedLocation visitedLocation = gpsUtil.getUserLocation(userId);

        //WHEN
        List<Attraction> nearByAttraction = tourGuideService.getNearByAttractions(visitedLocation);

        //THEN
        assertNotNull(nearByAttraction);
    }


}
