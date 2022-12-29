package tourGuide;

import com.google.common.net.HttpHeaders;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tourGuide.helper.InternalTestHelper;
import tourGuide.newRewardCentral.RewardCentral;
import tourGuide.service.GpsUtilService;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;

import java.io.IOException;
import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
public class TestNearestAttractions {

    private static MockWebServer mockWebServer;

    @Autowired
    MockMvc mockMvc;

    @BeforeClass
    public static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(8088);
    }

    @BeforeClass
    public static void teardown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void the5nearestAttractions() {

        MockResponse mockResponse = new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setResponseCode(200)
                .setBody("{}");

        mockWebServer.enqueue(mockResponse);

        //Given
        GpsUtilService gpsUtil = new GpsUtilService();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentral());
        InternalTestHelper.setInternalUserNumber(1);
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
        tourGuideService.getUser("internalUser0");

        //When
        List<OutputAttraction> resultat = tourGuideService.the5NearestAttractions("internalUser0");
        System.out.println(resultat);

        //Then
        Assert.assertNotNull(resultat);
        Assert.assertEquals(5, resultat.size());


    }

}
