package reward;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RewardController {

    private Logger logger = LoggerFactory.getLogger(RewardController.class);
    @Autowired
    private RewardService rewardService;

}
