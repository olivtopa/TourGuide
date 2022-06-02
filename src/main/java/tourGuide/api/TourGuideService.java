package tourGuide.api;


import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import tourGuide.model.AttractionNearBy;
import tourGuide.model.LocationExtended;
import tourGuide.model.ProviderExtended;
import tourGuide.model.VisitedLocationExtended;
import tourGuide.user.User;
import tourGuide.user.UserReward;
import tripPricer.Provider;

import java.util.List;
import java.util.Map;

// Implemented in TourGuideServiceImpl
public interface TourGuideService {


    final static int NUMBER_OF_PROPOSED_ATTRACTIONS = 5;

    List<UserReward> getUserRewards(User user);

    VisitedLocationExtended getLastUserLocation(User user);

    Map<String, LocationExtended> getLastLocationAllUsers();

    List<ProviderExtended> getTripDeals(User user);

    List<AttractionNearBy> getNearByAttractions(String userName);
}
