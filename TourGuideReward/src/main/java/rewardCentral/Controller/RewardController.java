package rewardCentral.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import rewardCentral.service.RewardCentralService;

import java.util.UUID;

@RestController
public class RewardController {

    @Autowired
    RewardCentralService rewardCentralService;

    @RequestMapping("/getRewardPoints")
    public int getAttractionRewardPoints(@RequestParam UUID attractionId, @RequestParam UUID userId){
        return rewardCentralService.getAttractionRewardPoints(attractionId,userId);
    }
}
