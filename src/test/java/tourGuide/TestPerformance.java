package tourGuide;

import static org.junit.Assert.assertTrue;

import java.util.*;
import java.util.concurrent.TimeUnit;

import org.apache.commons.lang3.time.StopWatch;
import org.junit.BeforeClass;
import org.junit.Test;

import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import rewardCentral.RewardCentral;
import tourGuide.api.GpsRequestService;
import tourGuide.api.RewardRequestService;
import tourGuide.api.TourGuideService;
import tourGuide.api.UserService;
import tourGuide.helper.InternalTestHelper;
import tourGuide.model.AttractionExtended;
import tourGuide.model.VisitedLocationExtended;
import tourGuide.service.RewardsService;

import tourGuide.tracker.TrackerService;
import tourGuide.user.User;


@RunWith(SpringRunner.class)
@SpringBootTest
public class TestPerformance {

	@Autowired private RewardRequestService rewardRequest;
	@Autowired private GpsRequestService gpsRequest;
	@Autowired private  TourGuideService tourGuideService;
	@Autowired private UserService userService;
	@Autowired private TrackerService trackerService;

	@BeforeClass
	public static void prepareLocale() {
		Locale.setDefault(Locale.ENGLISH);
	}
	
	/*
	 * A note on performance improvements:
	 *     
	 *     The number of users generated for the high volume tests can be easily adjusted via this method:
	 *     
	 *     		InternalTestHelper.setInternalUserNumber(100000);
	 *     
	 *     
	 *     These tests can be modified to suit new solutions, just as long as the performance metrics
	 *     at the end of the tests remains consistent. 
	 * 
	 *     These are performance metrics that we are trying to hit:
	 *     
	 *     highVolumeTrackLocation: 100,000 users within 15 minutes:
	 *     		assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
     *
     *     highVolumeGetRewards: 100,000 users within 20 minutes:
	 *          assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	 */
	
	//@Ignore
	@Test
	public void highVolumeTrackLocation() {

			// Users should be incremented up to 100,000, and test finishes within 15 minutes
			InternalTestHelper.setInternalUserNumber(100000);

			StopWatch stopWatch = new StopWatch();
			stopWatch.start();
			trackerService.trackAllUsers();
			stopWatch.stop();
			trackerService.stopTracking();

			System.out.println("highVolumeTrackLocation: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
			assertTrue(TimeUnit.MINUTES.toSeconds(15) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}
	
	//@Ignore
	@Test
	public void highVolumeGetRewards() {

			RewardCentral rewardCentral = new RewardCentral();
			RewardsService rewardsService = new RewardsService(gpsRequest,rewardCentral);

			// Users should be incremented up to 100,000, and test finishes within 20 minutes
			InternalTestHelper.setInternalUserNumber(100000);
			StopWatch stopWatch = new StopWatch();
			stopWatch.start();


			AttractionExtended attraction = gpsRequest.getAllAttractions().get(0);
			List<User> allUsers = new ArrayList<>();
			allUsers = userService.getAllUsers();
			allUsers.forEach(u -> u.addToVisitedLocations(new VisitedLocationExtended(u.getUserId(), attraction, new Date())));

			allUsers.forEach(u -> rewardsService.calculateRewards(u));

			for(User user : allUsers) {
				assertTrue(user.getUserRewards().size() > 0);
			}
			stopWatch.stop();
			trackerService.stopTracking();

			System.out.println("highVolumeGetRewards: Time Elapsed: " + TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()) + " seconds.");
			assertTrue(TimeUnit.MINUTES.toSeconds(20) >= TimeUnit.MILLISECONDS.toSeconds(stopWatch.getTime()));
	}
	
}
