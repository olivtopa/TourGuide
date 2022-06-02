package tourGuide.api;

import gpsUtil.location.Attraction;
import tourGuide.model.AttractionExtended;
import tourGuide.user.User;

import java.util.List;

public interface RewardRequestService {

    List<User> addAllNewRewardsAllUsers(List<User> userList, List<AttractionExtended> attractions);

    int sumOfAllRewardPoints(User user);

    int getRewardPoints(AttractionExtended attraction, User user);
}
