package tourGuide;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tourGuide.helper.InternalTestHelper;
import tourGuide.newGpsUtil.Attraction;
import tourGuide.newGpsUtil.VisitedLocation;
import tourGuide.service.GpsUtilService;
import tourGuide.service.RewardCentralService;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.user.User;
import tourGuide.user.UserReward;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class TestRewardsService {


    //@Mock
  //  private GpsUtilService gpsUtil; // client d'appel du module TourGuideGPS


    //@Mock
    private RewardCentralService rewardCentralService;  // client d'appel du module TourGuide Reward
    //@Mock
    private RewardsService rewardsService;  // Service de TourGuide

    @Test
    public void userGetRewards() throws ExecutionException, InterruptedException {

        GpsUtilService gpsUtil = new GpsUtilService();
        RewardsService rewardsService = new RewardsService(gpsUtil, rewardCentralService);
        rewardsService.setProximityBuffer(Integer.MAX_VALUE);
        //GIVEN
        InternalTestHelper.setInternalUserNumber(1);
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
        User user = tourGuideService.getAllUsers().get(0);
        VisitedLocation visitedLocation = tourGuideService.getUserLocation(user);
        System.out.println("nombre de users ; " + tourGuideService.getAllUsers().size());
        //User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
       /* List<Attraction> attractions = new ArrayList();
        Attraction attraction = new Attraction();
        attraction.setAttractionName("Disneyland");
        attraction.setCity("Anaheim");
        attraction.setState("CA");
        attractions.add(attraction);*/

       /* VisitedLocation visitedLocation = new VisitedLocation();
        visitedLocation.setUserId(user.getUserId());
        visitedLocation.setLocation(gpsUtil.getUserLocation(user.getUserId()).getLocation());
        visitedLocation.setTimeVisited(user.getLatestLocationTimestamp());*/

        //Mockito.when(gpsUtil.getAttractions()).thenReturn(attractions);
        System.out.println(gpsUtil.getAttractions().get(0).getAttractionName());
        user.addToVisitedLocations(visitedLocation);
        System.out.println(user.getVisitedLocations().get(0).getUserId());

        //Mock des appels de méthodes de tourGuideService.trackUserLocation
//        Mockito.when(gpsUtil.getUserLocation(user.getUserId())).thenReturn(visitedLocation);
        System.out.println("le visitedLocation.getLocation() :"+ visitedLocation.getLocation());

        //WHEN
        //VisitedLocation resultat = tourGuideService.trackUserLocation(user).join();
        //tourGuideService.tracker.stopTracking();
        rewardsService.calculateRewards(user);

        //THEN
        //Thread.sleep(1000);
        //TODO supprimer
        assertEquals(2,user.getUserRewards().size());
       // System.out.println("userID :" +resultat);
    }

    @Test
    public void isWithinAttractionProximity() {

        //GIVEN
        GpsUtilService gpsUtil = new GpsUtilService();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentralService());
        List<Attraction> attractions = new ArrayList();
        Attraction attraction = new Attraction();
        attraction.setAttractionName("Disneyland");
        attraction.setCity("Anaheim");
        attraction.setState("CA");
        attractions.add(attraction);
        //WHEN-THEN
        assertTrue(rewardsService.isWithinAttractionProximity(attraction, attraction.getLocation()));
    }

    //@Ignore // Needs fixed - can throw ConcurrentModificationException
    @Test
    public void nearAllAttractions() throws ExecutionException, InterruptedException {

        GpsUtilService gpsUtil = new GpsUtilService();
        RewardsService rewardsService = new RewardsService(gpsUtil, rewardCentralService);
        InternalTestHelper.setInternalUserNumber(1);
        //GIVEN
        rewardsService.setProximityBuffer(Integer.MAX_VALUE);
        User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");

        List<Attraction> attractions = new ArrayList();
        Attraction attraction = new Attraction();
        attraction.setAttractionName("Disneyland");
        attraction.setCity("Anaheim");
        attraction.setState("CA");
        attractions.add(attraction);
        Mockito.when(gpsUtil.getAttractions()).thenReturn(attractions);
        VisitedLocation visitedLocation = new VisitedLocation();
        visitedLocation.setUserId(user.getUserId());
        visitedLocation.setLocation(attraction.getLocation());
        visitedLocation.setTimeVisited(user.getLatestLocationTimestamp());

        Mockito.when(gpsUtil.getAttractions()).thenReturn(attractions);
        System.out.println(attraction.getAttractionName());
        user.addToVisitedLocations(visitedLocation);


        //WHEN
        rewardsService.calculateRewards(user);
        Thread.sleep(1000);
        List<UserReward> userRewards = user.getUserRewards();
        //THEN
        assertEquals(gpsUtil.getAttractions().size(), userRewards.size());
    }

}
