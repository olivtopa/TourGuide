package tourGuide.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


import org.springframework.stereotype.Service;

import tourGuide.newGpsUtil.Attraction;
import tourGuide.newGpsUtil.Location;
import tourGuide.newGpsUtil.VisitedLocation;

import tourGuide.user.User;
import tourGuide.user.UserReward;

@Service
public class RewardsService {
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
    private GpsUtilService gpsUtil;
      private List<Attraction> attractions;


    private final ExecutorService executor = Executors.newFixedThreadPool(1000);

    // proximity in miles
    private int defaultProximityBuffer = 10;
    private int proximityBuffer = defaultProximityBuffer;
    private int attractionProximityRange = 200;

    private RewardCentralService rewardsCentralService;

    public RewardsService(GpsUtilService gpsUtil, RewardCentralService rewardCentralService) {
        this.gpsUtil = gpsUtil;
        this.rewardsCentralService = rewardCentralService;
    }

     public void setProximityBuffer(int proximityBuffer) {
        this.proximityBuffer = proximityBuffer;
    }

    public void setDefaultProximityBuffer() {
        proximityBuffer = defaultProximityBuffer;
    }

    public void calculateRewards(User user) {
        List<VisitedLocation> userLocations = new ArrayList<>(user.getVisitedLocations());
        for (VisitedLocation visitedLocation : userLocations) {
            this.getAttractions().stream().forEach(attraction -> {
                if (user.getUserRewards().stream().filter(r -> r.getAttraction().getAttractionName().equals(attraction.getAttractionName())).count() == 0) {
                    if (nearAttraction(visitedLocation, attraction)) {
                        CompletableFuture.runAsync(() ->
                                user.addUserReward(new UserReward(visitedLocation, attraction, getRewardPoints(attraction, user))), executor);
                    }
                }
            });
        };
    }

    public boolean isWithinAttractionProximity(Attraction attraction, Location location) {
        return getDistance(attraction.getLocation(), location) > attractionProximityRange ? false : true;
    }

    private boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction) {
        return getDistance(attraction.getLocation(), visitedLocation.getLocation()) > proximityBuffer ? false : true;
    }

    private int getRewardPoints(Attraction attraction, User user) {
        return rewardsCentralService.getRewardPoints(attraction.getAttractionId(), user.getUserId());
    }

    public double getDistance(Location loc1, Location loc2) {
        double lat1 = Math.toRadians(loc1.getLatitude());
        double lon1 = Math.toRadians(loc1.getLongitude());
        double lat2 = Math.toRadians(loc2.getLatitude());
        double lon2 = Math.toRadians(loc2.getLongitude());

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        double nauticalMiles = 60 * Math.toDegrees(angle);
        double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
        return statuteMiles;
    }
// Lazi Init pour eviter l'init a chaque instantiation de la classe
    private List<Attraction> getAttractions() {
        if (attractions != null) {
            return attractions;
        }
        attractions = gpsUtil.getAttractions();
        return attractions;
    }

}
