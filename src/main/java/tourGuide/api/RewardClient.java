package tourGuide.api;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tourGuide.user.User;
import tourGuide.user.UserAttraction;
import tourGuide.user.UserAttractionLists;

import java.util.List;

@FeignClient(name="reward", url="http://reward:8082")
public interface RewardClient {

    @PatchMapping("/addAllNewRewardsAllUsers")
    List<User> addAllNewRewardsAllUsers(@RequestBody UserAttractionLists attractionUserLists);

    @GetMapping("/sumOfAllRewardPoints")
    int sumOfAllRewardPoints(@RequestBody User user);

    @GetMapping("/getRewardPoints")
    int getRewardPoints(@RequestBody UserAttraction userAttraction);
}
