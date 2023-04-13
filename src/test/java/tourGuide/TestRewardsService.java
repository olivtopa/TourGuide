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

    private static MockWebServer mockWebServer;

    @Autowired
    private MockMvc mockMvc;

    @BeforeClass
    public static void setup() throws IOException{
        mockWebServer = new MockWebServer();
        mockWebServer.start(8081);
    }

    @AfterClass
    public static void tearDown() throws  IOException{
        mockWebServer.shutdown();
    }

    @Mock
    private GpsUtilService gpsUtil;
    @Mock
    private RewardCentralService rewardCentralService;
    @Mock
    private RewardsService rewardsService;

    @Test
    public void userGetRewards() throws ExecutionException, InterruptedException {
        //GIVEN
        InternalTestHelper.setInternalUserNumber(0);
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
        User user = new User(UUID.randomUUID(), "jon", "000", "jon@tourGuide.com");
        List<Attraction> attractions = new ArrayList();
        Attraction attraction = new Attraction();
        attraction.setAttractionName("Disneyland");
        attraction.setCity("Anaheim");
        attraction.setState("CA");
        attractions.add(attraction);

        VisitedLocation visitedLocation = new VisitedLocation();
        visitedLocation.setUserId(user.getUserId());
        visitedLocation.setLocation(attraction.getLocation());
        visitedLocation.setTimeVisited(user.getLatestLocationTimestamp());

        Mockito.when(gpsUtil.getAttractions()).thenReturn(attractions);
        System.out.println(attraction.getAttractionName());
        user.addToVisitedLocations(visitedLocation);

        //Mock des appels de méthodes de tourGuideService.trackUserLocation
        Mockito.when(gpsUtil.getUserLocation(user.getUserId())).thenReturn(visitedLocation);
        System.out.println("le visitedLocation.getLocation() :"+ visitedLocation.getLocation());

        //WHEN
        VisitedLocation resultat = tourGuideService.trackUserLocation(user).join();
        tourGuideService.tracker.stopTracking();

        //THEN
        assertEquals(user.getUserId(),resultat.getUserId());
        System.out.println("userID :" +resultat);
    }

    @Test
    public void isWithinAttractionProximity() {

        MockResponse mockResponse = new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setResponseCode(200)
                .setBody("{}");

        mockWebServer.enqueue(mockResponse);

        //GIVEN
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
    public void calculateRewardsTest() throws ExecutionException, InterruptedException {
        InternalTestHelper.setInternalUserNumber(1);
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
        //GIVEN
        rewardsService.setProximityBuffer(Integer.MAX_VALUE);

        List<Attraction> attractions = new ArrayList();
        Attraction attraction = new Attraction();
        attraction.setAttractionName("Disneyland");
        attraction.setCity("Anaheim");
        attraction.setState("CA");
        attractions.add(attraction);
        Mockito.when(gpsUtil.getAttractions()).thenReturn(attractions);
        Mockito.doNothing().when(rewardsService).calculateRewards(tourGuideService.getAllUsers().get(0));
        //WHEN
        rewardsService.calculateRewards(tourGuideService.getAllUsers().get(0));
        tourGuideService.tracker.stopTracking();
        List<UserReward> userRewards = tourGuideService.getUserRewards(tourGuideService.getAllUsers().get(0));
        //THEN
        assertEquals(gpsUtil.getAttractions().size(), userRewards.size());
    }

}
