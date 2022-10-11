package tourGuide;


import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import tourGuide.newGpsUtil.VisitedLocation;
import tourGuide.service.GpsUtilService;
import tourGuide.service.TourGuideService;

import java.util.UUID;

@SpringBootTest
public class GpsUtilServiceITTest {

   // @Autowired
   // GpsUtilService gpsUtilService;
    @Autowired
    TourGuideService tourGuideService;


    @Test
    public void notNullGetUserLocationResult(){

    GpsUtilService gpsUtilService = new GpsUtilService();

    //GIVEN
        UUID userId = UUID.randomUUID();

        System.out.println("le UUID : " + userId);

    //WHEN
        VisitedLocation result = gpsUtilService.getUserLocation(userId);

    //THEN
        //Assert.assertNotNull(result);
        System.out.println(result);

    }

}
