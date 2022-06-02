package trip;

import tourGuide.model.AttractionNearBy;
import tourGuide.model.ProviderExtended;
import tourGuide.user.User;

import java.util.List;

public interface TripService {

    final static String TRIP_PRICER_KEY = "test-server-api-key";

    List<ProviderExtended> calculateProposals(User user, List<AttractionNearBy> attractions, int cumulativeRewardPoint);
}
