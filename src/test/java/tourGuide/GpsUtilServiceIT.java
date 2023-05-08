package tourGuide;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import tourGuide.newGpsUtil.Attraction;
import tourGuide.newGpsUtil.VisitedLocation;
import tourGuide.service.GpsUtilService;

import java.util.List;
import java.util.UUID;

@SpringBootTest
public class GpsUtilServiceIT {
        @Test
    public void notNullGetUserLocationResult() {
        GpsUtilService gpsUtilService = new GpsUtilService();

        //GIVEN
        UUID userId = UUID.randomUUID();

        //WHEN
        VisitedLocation result = gpsUtilService.getUserLocation(userId);

        //THEN
        Assert.assertNotNull(result);
    }


    @Test
    public void notNullGetAttractions() {

        //GIVEN
        GpsUtilService gpsUtilService = new GpsUtilService();

        //WHEN
        List<Attraction> result = gpsUtilService.getAttractions();

        //THEN
        // tester avec NotEqual 0
        Assert.assertNotEquals(0,result.size());
    }
}
