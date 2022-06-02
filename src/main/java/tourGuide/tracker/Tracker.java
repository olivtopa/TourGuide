package tourGuide.tracker;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import gpsUtil.location.Attraction;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tourGuide.api.GpsRequestService;
import tourGuide.api.RewardRequestService;
import tourGuide.api.TourGuideService;
import tourGuide.api.UserService;
import tourGuide.model.AttractionExtended;

import tourGuide.user.User;

@Service
public class Tracker extends Thread implements TrackerService{

	private Logger logger = LoggerFactory.getLogger(Tracker.class);
	private static final long trackingPollingInterval = TimeUnit.MINUTES.toSeconds(5);
	private final ExecutorService executorService = Executors.newSingleThreadExecutor();
	private TourGuideService tourGuideService;
	private boolean stop = false;

	@Autowired
	private UserService userService;
	@Autowired private GpsRequestService gpsRequest;
	@Autowired private RewardRequestService rewardRequest;

	public Tracker(TourGuideService tourGuideService) {
		this.tourGuideService = tourGuideService;
		executorService.submit(this);
	}


	public Tracker() {
		logger.debug("new instance");
		executorService.submit(this);
	}

	/**
	 * Assures to shut down the Tracker thread
	 */
	public void stopTracking() {
		stop = true;
		executorService.shutdownNow();
	}

	/*
	 * Launch a tracker daemon, which will track all users every 5 minutes.
	 * @see tripmaster.tourguide.tracker.TrackerServiceImpl.trackAllUsers
	 */
	@Override
	public void run() {
		logger.debug("run() starting");
		while(true) {
			if(Thread.currentThread().isInterrupted() || stop) {
				logger.debug("run() has been told to stop");
				break;
			}
			trackAllUsers();
			try {
				logger.debug("run() waiting for next iteration");
				TimeUnit.SECONDS.sleep(trackingPollingInterval);
			} catch (InterruptedException e) {
				logger.error("run() catched InterruptedException");
				break;
			}
		}
		logger.debug("run() reached the end");
	}

	/**
	 * Computes a full update of the user information for each user in the ecosystem :
	 * get the current location, store it into the user visited location history, add user rewards for newly visited attractions.
	 */
	@Override
	public void trackAllUsers() {
		logger.debug("trackAllUsers starts iteration over all users");
		// Get All users
		List<User> allUsersStart = userService.getAllUsers();
		// Get and register current location for all users
		List<User> allUsersUpdated = gpsRequest.trackAllUserLocations(allUsersStart);
		// Get all attractions
		List<AttractionExtended> allAttractions = gpsRequest.getAllAttractions();
		// Update rewards for all users
		rewardRequest.addAllNewRewardsAllUsers(allUsersUpdated, allAttractions);
	}
}
