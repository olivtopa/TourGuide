package reward;

import tourGuide.model.AttractionExtended;
import tourGuide.model.VisitedLocationExtended;
import tourGuide.user.User;

import java.util.List;

public interface RewardService {

    void setProximityMaximalDistance(int proximityBuffer);

    int getProximityMaximalDistance();

    boolean nearAttraction(VisitedLocationExtended visitedLocation, AttractionExtended attractionData);

    int getRewardPoints(AttractionExtended attractionData, User user);

    void addAllNewRewards(User user, List<AttractionExtended> attractions);

    List<User> addAllNewRewardsAllUsers(List<User> userList, List<AttractionExtended> attractions);

    int sumOfAllRewardPoints(User user);

}
