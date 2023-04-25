package tourGuide;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.junit.Test;
import tourGuide.newGpsUtil.Attraction;
import tourGuide.newGpsUtil.VisitedLocation;
import tourGuide.service.GpsUtilService;
import tourGuide.service.RewardCentralService;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import static org.junit.Assert.assertNotNull;


@SpringBootTest
@AutoConfigureMockMvc
public class NearbyAttractionsIT {

    @Test
    public void getNearbyAttractions() throws ExecutionException, InterruptedException {

        //GIVEN
        GpsUtilService gpsUtil = new GpsUtilService();

        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentralService());

        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);

        UUID userId = UUID.randomUUID();
        VisitedLocation visitedLocation = gpsUtil.getUserLocation(userId);

        //WHEN
        List<Attraction> nearByAttraction = tourGuideService.getNearByAttractions(visitedLocation);

        //THEN
        assertNotNull(nearByAttraction);
    }

}
