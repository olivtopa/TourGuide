package tourGuide.user;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import tourGuide.model.AttractionExtended;
import tourGuide.model.VisitedLocationExtended;

public class UserReward {

	public final VisitedLocationExtended visitedLocation;
	public final AttractionExtended attraction;
	private int rewardPoints;
	public UserReward(VisitedLocationExtended visitedLocation, AttractionExtended attraction, int rewardPoints) {
		this.visitedLocation = visitedLocation;
		this.attraction = attraction;
		this.rewardPoints = rewardPoints;
	}
	
	public UserReward(VisitedLocationExtended visitedLocation, AttractionExtended attraction) {
		this.visitedLocation = visitedLocation;
		this.attraction = attraction;
	}

	public void setRewardPoints(int rewardPoints) {
		this.rewardPoints = rewardPoints;
	}
	
	public int getRewardPoints() {
		return rewardPoints;
	}
	
}
