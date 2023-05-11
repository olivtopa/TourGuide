package tourGuide;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import tourGuide.newGpsUtil.Attraction;
import tourGuide.newGpsUtil.Location;
import tourGuide.newGpsUtil.VisitedLocation;
import tourGuide.service.GpsUtilService;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class TestNearByAttractions {


    @Mock
    GpsUtilService gpsUtil;
    @Mock
    RewardsService rewardsService;
    @Test
    public void attractionsAreNotNearUsers(){

        //Given
        Location location = new Location();
        location.setLatitude(20);
        location.setLongitude(30);

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
                thenReturn(true);

        //When
        TourGuideService tourGuideService=new TourGuideService(gpsUtil,rewardsService);
        List<Attraction> result = tourGuideService.getNearByAttractions(visitedLocation);
        System.out.println(result.get(0));


       //Then
        assert!result.isEmpty():"Il n'y a pas d'attraction à proximite";
    }
}
