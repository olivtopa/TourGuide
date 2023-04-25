package tourGuide;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tourGuide.newGpsUtil.Attraction;
import tourGuide.newGpsUtil.VisitedLocation;
import tourGuide.service.GpsUtilService;
import tourGuide.service.RewardCentralService;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class GpsUtilServiceITTest {

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
        Assert.assertNotNull(result.size());
    }
}
