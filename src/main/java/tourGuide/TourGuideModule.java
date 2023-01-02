package tourGuide;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.zalando.jackson.datatype.money.MoneyModule;
import tourGuide.newRewardCentral.RewardCentral;
import tourGuide.service.GpsUtilService;
import tourGuide.service.RewardCentralService;
import tourGuide.service.RewardsService;

@Configuration
public class TourGuideModule {


   /* @Bean
    public RewardsService getRewardsService(GpsUtilService gpsUtilService) {
        return new RewardsService(gpsUtilService, rewardCentralService());
    }

    @Bean
    public RewardCentralService rewardCentralService() {
        return new RewardCentralService();
    }

    @Bean
    public MoneyModule moneyModule() {
        return new MoneyModule();
    }*/
}
