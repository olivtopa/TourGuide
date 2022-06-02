package tourGuide.model;

import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import tourGuide.service.RewardsService;
import tourGuide.user.User;

import java.util.UUID;

public class AttractionNearBy {
    public UUID id;
    public String name;
    public LocationExtended attractionLocation;
    public LocationExtended userLocation;
    public double distance;
    public int rewardPoints;

    public  AttractionNearBy(AttractionExtended attraction, User user, int rewardPoints) {
        id = attraction.id;
        name = attraction.name;
        attractionLocation = new LocationExtended(attraction.latitude, attraction.longitude);
        VisitedLocationExtended visitedLocation = user.getLastVisitedLocation();
        userLocation = new LocationExtended(visitedLocation.location.latitude, visitedLocation.location.longitude);
        distance = userLocation.getDistance(attractionLocation);
        this.rewardPoints = rewardPoints;
    }

    public AttractionNearBy() {
        id = new UUID(0,0);
        name = new String();
    }
}
