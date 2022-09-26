package tourGuide;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


import org.zalando.jackson.datatype.money.MoneyModule;
import rewardCentral.RewardCentral;
import tourGuide.service.GpsUtilService;
import tourGuide.service.RewardsService;

@Configuration
public class TourGuideModule {
	
	@Bean
	public GpsUtilService getGpsUtil() {
		return new GpsUtilService();
	}
	
	@Bean
	public RewardsService getRewardsService() {
		return new RewardsService(getGpsUtil(), getRewardCentral());
	}
	
	@Bean
	public RewardCentral getRewardCentral() {
		return new RewardCentral();
	}

	@Bean
	public MoneyModule moneyModule(){
		return new MoneyModule();
	}
	
}
