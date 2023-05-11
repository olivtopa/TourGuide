package tourGuide.unit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import tourGuide.helper.InternalTestHelper;
import tourGuide.newGpsUtil.Attraction;
import tourGuide.newGpsUtil.Location;
import tourGuide.newGpsUtil.VisitedLocation;
import tourGuide.service.GpsUtilService;
import tourGuide.service.RewardCentralService;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import tourGuide.user.User;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public class TourGuideServiceTest {


    @Mock
    GpsUtilService gpsUtil;
    @Mock
    RewardsService rewardsService;
    @Test
    public void attractionsAreNotNearUsers(){

        //Given
        Location location = new Location();
        location.setLatitude(20000000);
        location.setLongitude(30000000);

        List<Attraction> attractions = new ArrayList();
        Attraction attraction = new Attraction();
        attraction.setLatitude(12);
        attraction.setLongitude(20);
        attractions.add(attraction);

        VisitedLocation visitedLocation =new VisitedLocation();
        visitedLocation.setLocation(location);
        System.out.println("Location: "+location.getLatitude()+" , "+location.getLongitude());
        System.out.println(("Attraction: "+attraction.getLocation().getLongitude() + " , "+attraction.getLocation().getLatitude()));

        Mockito.when(gpsUtil.getAttractions()).thenReturn(attractions);
        Mockito.when(rewardsService.isWithinAttractionProximity(attraction, visitedLocation.getLocation())).
                thenReturn(false);

        //When
        TourGuideService tourGuideService=new TourGuideService(gpsUtil,rewardsService);
        List<Attraction> result = tourGuideService.getNearByAttractions(visitedLocation);


       //Then
        assertTrue(result.isEmpty());
    }

    @Test
    public void addUser() {

        //GIVEN
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
    public void getAllUsers() {
        //GIVEN
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

}
