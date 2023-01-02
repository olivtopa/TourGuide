package tourGuide;


import com.google.common.net.HttpHeaders;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tourGuide.helper.InternalTestHelper;
import tourGuide.newGpsUtil.Location;
import tourGuide.newRewardCentral.RewardCentral;
import tourGuide.service.GpsUtilService;
import tourGuide.service.RewardCentralService;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;

import java.io.IOException;
import java.util.Map;

;

@SpringBootTest
@AutoConfigureMockMvc
public class TestLasUsersLocation {

    private static MockWebServer mockWebServer;

    @Autowired
    MockMvc mockMvc;

    @BeforeClass
    public static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(8081);
    }

    @AfterClass
    public static void teardown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void isListOfLocationEmpty() {

        MockResponse mockResponse = new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setResponseCode(200)
                .setBody("{}");

        mockWebServer.enqueue(mockResponse);

        //Given
        GpsUtilService gpsUtil = new GpsUtilService();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentralService());
        InternalTestHelper.setInternalUserNumber(5);
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);

        //When
        Map<String, Location> resultat = tourGuideService.lastUsersLocation();
        System.out.println(resultat);

        //Then
        Assert.assertEquals(5, resultat.size());
    }
}
