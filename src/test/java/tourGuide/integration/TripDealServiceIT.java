package tourGuide.integration;

import org.junit.Test;
import tourGuide.NewTripPricer.Provider;
import tourGuide.service.*;
import tourGuide.user.User;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class TripDealServiceIT {
    @Test
    public void getTripDeals() {
        //GIVEN
        TripDealService tripDealService= new TripDealService(new TripPricerService());
        User user = new User(UUID.randomUUID(),"","","");
        user.getUserPreferences().setNumberOfChildren(2);

        //WHEN
        List<Provider> listOfProvider = tripDealService.getTripDeals(user);

        //THEN
        assertNotNull(listOfProvider);
        assertFalse(listOfProvider.isEmpty());
    }
}
