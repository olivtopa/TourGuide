package tourGuide.service;

import tourGuide.NewTripPricer.Provider;
import tourGuide.user.User;

import java.util.List;

public class TripDealService {
    private final TripPricerService tripPricerService = new TripPricerService();
    private static final String tripPricerApiKey = "test-server-api-key";

    public List<Provider> getTripDeals(User user) {
        int cumulativeRewardPoints = user.getUserRewards().stream().mapToInt(i -> i.getRewardPoints()).sum();
        List<Provider> providers = tripPricerService.getPrice(tripPricerApiKey, user.getUserId(), user.getUserPreferences().getNumberOfAdults(),
                user.getUserPreferences().getNumberOfChildren(), user.getUserPreferences().getTripDuration(), cumulativeRewardPoints);
        user.setTripDeals(providers);
        return providers;
    }
}
