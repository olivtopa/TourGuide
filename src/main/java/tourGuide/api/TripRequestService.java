package tourGuide.api;

import tourGuide.model.AttractionNearBy;
import tourGuide.model.ProviderExtended;
import tourGuide.user.User;
import tripPricer.Provider;

import java.util.List;

public interface TripRequestService {

    List<ProviderExtended> calculateProposals(User user, List<AttractionNearBy> attractions, int cumulativeRewardPoints);
}
