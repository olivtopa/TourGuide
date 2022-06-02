package tourGuide.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.ExecutionException;

import org.springframework.stereotype.Service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import rewardCentral.RewardCentral;
import tourGuide.api.GpsRequestService;
import tourGuide.model.AttractionExtended;
import tourGuide.model.LocationExtended;
import tourGuide.model.VisitedLocationExtended;
import tourGuide.user.User;
import tourGuide.user.UserReward;

@Service
public class RewardsService {

	private static final double STATUTE_MILES_PER_NAUTICAL_MILE = 1.15077945;
	private static final double EARTH_RADIUS_IN_NAUTICAL_MILES = 3440.0647948;


	// proximity in miles
    private int defaultProximityBuffer = 10;
	private int proximityBuffer = defaultProximityBuffer;
	private int attractionProximityRange = 200;
	private final GpsRequestService gpsUtil;
	private final RewardCentral rewardsCentral;
	
	public RewardsService(GpsRequestService gpsUtil, RewardCentral rewardCentral) {
		this.gpsUtil = gpsUtil;
		this.rewardsCentral = rewardCentral;
	}
	
	public void setProximityBuffer(int proximityBuffer) {
		this.proximityBuffer = proximityBuffer;
	}
	
	public void setDefaultProximityBuffer() {
		proximityBuffer = defaultProximityBuffer;
	}
	
	public void calculateRewards(User user) {
		List<VisitedLocationExtended> userLocations = new ArrayList<> (user.getVisitedLocations());
		List<AttractionExtended> attractions = gpsUtil.getAllAttractions();

		for(VisitedLocationExtended visitedLocation : userLocations) {
			CompletableFuture[] objects = attractions.stream().map(attraction -> {
				CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(()-> {
					if(user.getUserRewards().stream().filter(r -> r.attraction.name.equals(attraction.name)).count() == 0) {
						if(nearAttraction(visitedLocation, attraction)) {
							user.addUserReward(new UserReward(visitedLocation, attraction, getRewardPoints(attraction, user)));
						}
					}});
				return completableFuture;
			}).toArray(CompletableFuture[]::new);
			CompletableFuture.allOf(objects).join();
			}
	}
	
	public boolean isWithinAttractionProximity(AttractionExtended attraction, LocationExtended location) {
		return getDistance(attraction, location) > attractionProximityRange ? false : true;
	}
	
	private boolean nearAttraction(VisitedLocationExtended visitedLocation, AttractionExtended attraction) {
		return getDistance(attraction, visitedLocation.location) > proximityBuffer ? false : true;
	}
	
	public int getRewardPoints(AttractionExtended attraction, User user) {
		return rewardsCentral.getAttractionRewardPoints(attraction.id, user.getUserId());
	}

	public static double getDistance(LocationExtended loc1, LocationExtended loc2) {
		double lat1 = Math.toRadians(loc1.latitude);
		double lon1 = Math.toRadians(loc1.longitude);
		double lat2 = Math.toRadians(loc2.latitude);
		double lon2 = Math.toRadians(loc2.longitude);

		double angleDistance = Math.acos(Math.sin(lat1) * Math.sin(lat2)
				+ Math.cos(lat1) * Math.cos(lat2) * Math.cos(lon1 - lon2));

		double nauticalMilesDistance = EARTH_RADIUS_IN_NAUTICAL_MILES * angleDistance;
		double statuteMilesDistance = STATUTE_MILES_PER_NAUTICAL_MILE * nauticalMilesDistance;
		return statuteMilesDistance;
	}


}
