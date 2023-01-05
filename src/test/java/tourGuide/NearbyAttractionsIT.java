package tourGuide;

import com.google.common.net.HttpHeaders;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import tourGuide.newGpsUtil.Attraction;
import tourGuide.newGpsUtil.VisitedLocation;
import tourGuide.newRewardCentral.RewardCentral;
import tourGuide.service.GpsUtilService;
import tourGuide.service.RewardCentralService;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;

import java.io.IOException;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import static org.junit.Assert.assertEquals;


@SpringBootTest
@AutoConfigureMockMvc
public class NearbyAttractionsIT {

    private static MockWebServer mockWebServer;

    @Autowired
    private MockMvc mockMvc;

    @BeforeClass
    public static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(8091);
    }

    @AfterClass
    public static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void getNearbyAttractions() throws ExecutionException, InterruptedException {

        MockResponse mockResponse = new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setResponseCode(200)
                .setBody("{}");

        mockWebServer.enqueue(mockResponse);

        //GIVEN
        GpsUtilService gpsUtil = new GpsUtilService();

        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentralService());


        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);


        UUID userId = UUID.randomUUID();
        VisitedLocation visitedLocation = gpsUtil.getUserLocation(userId);


        //WHEN
        List<Attraction> nearByAttraction = tourGuideService.getNearByAttractions(visitedLocation);

        //tourGuideService.tracker.stopTracking();

        //THEN
        assertEquals(200, mockResponse);
    }

}
