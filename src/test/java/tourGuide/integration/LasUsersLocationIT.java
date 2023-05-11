package tourGuide.integration;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tourGuide.helper.InternalTestHelper;
import tourGuide.newGpsUtil.Location;
import tourGuide.service.GpsUtilService;
import tourGuide.service.RewardCentralService;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;
import java.util.Map;

@SpringBootTest
public class LasUsersLocationIT {
    @Test
    public void isListOfLocationEmpty() {

        //Given
        GpsUtilService gpsUtil = new GpsUtilService();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentralService());
        InternalTestHelper.setInternalUserNumber(5);
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);

        //When
        Map<String, Location> result = tourGuideService.lastUsersLocation();
        System.out.println(result);

        //Then
        Assert.assertEquals(5, result.size());
    }
}
