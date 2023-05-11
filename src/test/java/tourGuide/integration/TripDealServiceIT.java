package tourGuide.integration;

import org.junit.Test;
import tourGuide.NewTripPricer.Provider;
import tourGuide.helper.InternalTestHelper;
import tourGuide.service.*;

import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class TripDealServiceIT {
    @Test
    public void getTripDeals() {
        //GIVEN
        GpsUtilService gpsUtil = new GpsUtilService();
        TripDealService tripDealService= new TripDealService(new TripPricerService());
        RewardsService rewardsService = new RewardsService(gpsUtil, new RewardCentralService());
        TourGuideService tourGuideService = new TourGuideService(gpsUtil, rewardsService);
        InternalTestHelper.setInternalUserNumber(0);
        String userName = "internalUser1";
        tourGuideService.getUser(userName).getUserPreferences().setNumberOfChildren(2);

        //WHEN
        List<Provider> listOfProvider = tripDealService.getTripDeals(tourGuideService.getUser(userName));

        //THEN
        assertNotNull(listOfProvider);
        assertFalse(listOfProvider.isEmpty());

    }

}
