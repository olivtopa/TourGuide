package tourGuide;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import tourGuide.newGpsUtil.Attraction;
import tourGuide.newGpsUtil.Location;
import tourGuide.newGpsUtil.VisitedLocation;
import tourGuide.service.GpsUtilService;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class TestNearByAttractions {


    @Mock
    GpsUtilService gpsUtil;
    @Mock
    RewardsService rewardsService;
    @Test
    public void attractionsAreNotNearUsers(){

        //Given
        Location location = new Location();
        location.setLatitude(2000);
        location.setLongitude(3000);

        List<Attraction> attractions = new ArrayList();
        Attraction attraction = new Attraction();
        attraction.setLatitude(12);
        attraction.setLongitude(20);
        attractions.add(attraction);

        VisitedLocation visitedLocation =new VisitedLocation();
        visitedLocation.setLocation(location);
        System.out.println("Location: "+location.getLatitude()+" , "+location.getLongitude());
        System.out.println(("Attraction: "+attraction.getLocation().getLongitude() + " , "+attraction.getLocation().getLatitude()));
        TourGuideService tourGuideService=new TourGuideService(gpsUtil,rewardsService);
        Mockito.when(gpsUtil.getAttractions()).thenReturn(attractions);

        //When
        List<Attraction> result = tourGuideService.getNearByAttractions(visitedLocation);

       //Then
        assertTrue(result.isEmpty());
    }
}
