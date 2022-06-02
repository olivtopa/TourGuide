package tourGuide.api;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tourGuide.model.AttractionExtended;
import tourGuide.user.User;
import tourGuide.user.UserAttraction;
import tourGuide.user.UserAttractionLists;

import java.util.List;

@Service
public class RewardRequestServiceImpl implements RewardRequestService {

    private Logger logger = LoggerFactory.getLogger(RewardRequestServiceImpl.class);
    private final RewardClient rewardClient;
    @Autowired private ObjectMapper objectMapper;
    @Autowired private UserService userService;

    public RewardRequestServiceImpl(RewardClient rewardClient){
        this.rewardClient = rewardClient;
    }

    @Override
    public List<User> addAllNewRewardsAllUsers(List<User> userLit, List<AttractionExtended> attractions) {
        UserAttractionLists userAttractionLists =new UserAttractionLists(attractions,userLit);
        List<User> updateUserList = rewardClient.addAllNewRewardsAllUsers(userAttractionLists);
        userService.setAllUsers(updateUserList);
        return updateUserList;

    }

    @Override
    public int sumOfAllRewardPoints(User user) {
    int result = rewardClient.sumOfAllRewardPoints(user);
    return result;
    }

    @Override
    public int getRewardPoints(AttractionExtended attraction, User user) {
        UserAttraction userAttraction = new UserAttraction(user, attraction);
        int result = rewardClient.getRewardPoints(userAttraction);
        return result;
    }

}
