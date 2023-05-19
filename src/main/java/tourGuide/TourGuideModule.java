package tourGuide;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.zalando.jackson.datatype.money.MoneyModule;
import tourGuide.service.GpsUtilService;
import tourGuide.service.RewardCentralService;
import tourGuide.service.RewardsService;
import tourGuide.service.TripPricerService;

@Configuration
public class TourGuideModule {
    GpsUtilService gpsUtilService;
    RewardCentralService rewardCentralService;
    TripPricerService tripPricerService;

    @Bean
    public RewardsService getRewardsService() {
        return new RewardsService(gpsUtilService, rewardCentralService);
    }

    @Bean
    public MoneyModule moneyModule() {
        return new MoneyModule();
    }
}
