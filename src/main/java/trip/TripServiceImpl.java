package trip;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tourGuide.model.AttractionNearBy;
import tourGuide.model.ProviderExtended;
import tourGuide.user.User;
import tourGuide.user.UserPreferences;
import tripPricer.Provider;
import tripPricer.TripPricer;

import java.util.ArrayList;
import java.util.List;

public class TripServiceImpl implements TripService {

    @Autowired private TripPricer tripPricer;

    private Logger logger = LoggerFactory.getLogger(TripServiceImpl.class);

    /* return a List of Providers */

    @Override
    public List<ProviderExtended> calculateProposals(User user, List<AttractionNearBy> attractions, int cumulativeRewardPoints) {
        logger.debug("calculateProposals userName = " + user.getUserName()
                + " and attractionList of size " + attractions.size()
                + " and rewardPoints = cumulativeRewardPoints");
        List<ProviderExtended> providers = new ArrayList<ProviderExtended>();
        for (AttractionNearBy a : attractions) {
            List<Provider> proposals = tripPricer.getPrice(
                    TRIP_PRICER_KEY, a.id,
                    user.userPreferences.getNumberOfAdults(),
                    user.userPreferences.getNumberOfChildren(),
                    user.userPreferences.getTripDuration(),
                    cumulativeRewardPoints);
            for (Provider provider : proposals) {
                providers.add(newProviderExtended(provider));
            }
        }
        return providers;
    }

    private ProviderExtended newProviderExtended(Provider provider) {
        return new ProviderExtended(provider.name, provider.price, provider.tripId);
    }
}
