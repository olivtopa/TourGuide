package tourGuide;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.junit.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import tourGuide.newGpsUtil.VisitedLocation;
import tourGuide.service.GpsUtilService;

import java.io.IOException;
import java.util.UUID;

@SpringBootTest
@AutoConfigureMockMvc
public class GpsUtilServiceITTest {

    private static MockWebServer mockWebServer;

    @Autowired
    private MockMvc mockMvc;

    @BeforeClass
    public static void setup() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start(8081);
    }

    @AfterClass
    public static void tearDown() throws IOException {
        mockWebServer.shutdown();
    }

    @Test
    public void notNullGetUserLocationResult() {

        MockResponse mockResponse = new MockResponse()
                .addHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON)
                .setResponseCode(200)
                .setBody("{}");

        mockWebServer.enqueue(mockResponse);

        GpsUtilService gpsUtilService = new GpsUtilService();

        //GIVEN
        UUID userId = UUID.randomUUID();

        //WHEN
        VisitedLocation result = gpsUtilService.getUserLocation(userId);

        //THEN
        Assert.assertNotNull(result);
    }
}
