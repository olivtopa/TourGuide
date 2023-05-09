package tourGuide;

import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import tourGuide.newGpsUtil.Attraction;
import tourGuide.newGpsUtil.Location;
import tourGuide.newGpsUtil.VisitedLocation;
import tourGuide.service.GpsUtilService;
import tourGuide.service.TourGuideService;

import java.util.ArrayList;
import java.util.List;

public class TestNearByAttractions {


    @Mock
    GpsUtilService gpsUtil;
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
        //TourGuideService tourGuideService=new TourGuideService(gpsUtil,reward);
        Mockito.when(gpsUtil.getAttractions()).thenReturn(attractions);

        //When

       // ToDO A terminer

    }
}
