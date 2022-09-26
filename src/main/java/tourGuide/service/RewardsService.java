package tourGuide.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import rewardCentral.RewardCentral;
import tourGuide.newGpsUtil.Attraction;
import tourGuide.newGpsUtil.Location;
import tourGuide.newGpsUtil.VisitedLocation;
import tourGuide.user.User;
import tourGuide.user.UserReward;

@Service
public class RewardsService {
    private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
	private GpsUtilService gpsUtil;
	GpsUtilService newAttractions = new GpsUtilService();
	private final List<Attraction> attractions= newAttractions.getAttractions();
	private final ExecutorService executor = Executors.newFixedThreadPool(70);

	// proximity in miles
    private int defaultProximityBuffer = 10;
	private int proximityBuffer = defaultProximityBuffer;
	private int attractionProximityRange = 200;

	private final RewardCentral rewardsCentral;

	public RewardsService(GpsUtilService gpsUtil, RewardCentral rewardCentral) {
		this.gpsUtil = gpsUtil;
		this.rewardsCentral = rewardCentral;
	}

	@Autowired GpsUtilService gpsUtilService;


	public void setProximityBuffer(int proximityBuffer) {
		this.proximityBuffer = proximityBuffer;
	}
	
	public void setDefaultProximityBuffer() {
		proximityBuffer = defaultProximityBuffer;
	}
	
	public void calculateRewards(@org.jetbrains.annotations.NotNull User user) {
		List<VisitedLocation> userLocations = new ArrayList<> (user.getVisitedLocations());


		for(VisitedLocation visitedLocation : userLocations) {
			this.attractions.stream().forEach(attraction -> {

					if(user.getUserRewards().stream().filter(r -> r.attraction.attractionName.equals(attraction.attractionName)).count() == 0) {
						if (nearAttraction(visitedLocation, attraction)) {
							CompletableFuture.runAsync(() ->
									user.addUserReward(new UserReward(visitedLocation, attraction, getRewardPoints(attraction, user))), executor);
						}
					}
			});
		};
	}
	
	public boolean isWithinAttractionProximity(Attraction attraction, Location location) {
		return getDistance(gpsUtilService.location, location) > attractionProximityRange ? false : true;
	}
	
	private boolean nearAttraction(VisitedLocation visitedLocation, Attraction attraction) {
		return getDistance(gpsUtilService.location, visitedLocation.location) > proximityBuffer ? false : true;
	}
	
	private int getRewardPoints(Attraction attraction, User user) {
		return rewardsCentral.getAttractionRewardPoints(attraction.attractionId, user.getUserId());
	}
	
	public double getDistance(Location loc1, Location loc2) {
        double lat1 = Math.toRadians(loc1.latitude);
        double lon1 = Math.toRadians(loc1.longitude);
        double lat2 = Math.toRadians(loc2.latitude);
        double lon2 = Math.toRadians(loc2.longitude);

        double angle = Math.acos(Math.sin(lat1) * Math.sin(lat2)
                               + Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

        double nauticalMiles = 60 * Math.toDegrees(angle);
        double statuteMiles = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMiles;
        return statuteMiles;
	}

}
