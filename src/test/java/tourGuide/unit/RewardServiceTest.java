package tourGuide.unit;

import org.junit.Test;
import tourGuide.newGpsUtil.Attraction;
import tourGuide.service.RewardsService;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class RewardServiceTest {
    @Test
    public void isWithinAttractionProximity() {

        //GIVEN
        RewardsService rewardsService = new RewardsService(null, null);
        List<Attraction> attractions = new ArrayList();
        Attraction attraction = new Attraction();
        attraction.setAttractionName("Disneyland");
        attraction.setCity("Anaheim");
        attraction.setState("CA");
        attractions.add(attraction);
        //WHEN-THEN
        assertTrue(rewardsService.isWithinAttractionProximity(attraction, attraction.getLocation()));
    }

}
