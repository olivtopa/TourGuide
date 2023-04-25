package tourGuide;


import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import tourGuide.helper.InternalTestHelper;
import tourGuide.service.GpsUtilService;
import tourGuide.service.RewardCentralService;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;

import java.util.List;


@SpringBootTest
@AutoConfigureMockMvc
public class TestNearestAttractions {


    @Test
    public void the5nearestAttractions() {


        //Given
        GpsUtilService gpsUtil = new GpsUtilService();
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentralService());
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
