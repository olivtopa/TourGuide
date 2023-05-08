package tourGuide.service;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.IntStream;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import tourGuide.NewTripPricer.Provider;
import tourGuide.OutputAttraction;
import tourGuide.helper.InternalTestHelper;
import tourGuide.newGpsUtil.Attraction;
import tourGuide.newGpsUtil.Location;
import tourGuide.newGpsUtil.VisitedLocation;
import tourGuide.tracker.Tracker;
import tourGuide.user.User;
import tourGuide.user.UserPreferences;
import tourGuide.user.UserReward;


@Service
public class TourGuideService {
    private Logger logger = LoggerFactory.getLogger(TourGuideService.class);
    private final RewardsService rewardsService;
    private final TripPricerService tripPricerService = new TripPricerService();
    public final Tracker tracker;
    boolean testMode = true;
    private final ExecutorService executor = Executors.newFixedThreadPool(1000);
    private final RewardCentralService rewardCentralService = new RewardCentralService();

    public TourGuideService(GpsUtilService gpsUtil, RewardsService rewardsService) {
        this.gpsUtil = gpsUtil;
        this.rewardsService = rewardsService;

        if (testMode) {
            logger.info("TestMode enabled");
            logger.debug("Initializing users");
            initializeInternalUsers();
            logger.debug("Finished initializing users");
        }
        tracker = new Tracker(this);
        addShutDownHook();
    }

    @Autowired
    GpsUtilService gpsUtil;

    public List<UserReward> getUserRewards(User user) {
        return user.getUserRewards();
    }

    public VisitedLocation getUserLocation(User user) throws ExecutionException, InterruptedException {

        VisitedLocation visitedLocation = (user.getVisitedLocations().size() > 0) ?
                gpsUtil.getUserLocation(user.getUserId()) :
                trackUserLocation(user).join();
        return visitedLocation;
    }

    public User getUser(String userName) {
        return internalUserMap.get(userName);
    }

    public List<User> getAllUsers() {
        return internalUserMap.values().stream().collect(Collectors.toList());
    }

    public void addUser(User user) {
        if (!internalUserMap.containsKey(user.getUserName())) {
            internalUserMap.put(user.getUserName(), user);
        }
    }

   /* public List<Provider> getTripDeals(User user) {
        int cumulativeRewardPoints = user.getUserRewards().stream().mapToInt(i -> i.getRewardPoints()).sum();
        List<Provider> providers = tripPricerService.getPrice(tripPricerApiKey, user.getUserId(), user.getUserPreferences().getNumberOfAdults(),
                user.getUserPreferences().getNumberOfChildren(), user.getUserPreferences().getTripDuration(), cumulativeRewardPoints);
        user.setTripDeals(providers);
        return providers;
    }*/


    public CompletableFuture<VisitedLocation> trackUserLocation(User user) {
        CompletableFuture<VisitedLocation> cf = CompletableFuture
                .supplyAsync(() -> gpsUtil.getUserLocation(user.getUserId()), executor)
                .thenApply(visitedLocation -> {
                    user.addToVisitedLocations(visitedLocation);
                    rewardsService.calculateRewards(user);
                    return visitedLocation;
                });
        return cf;
    }


    public List<Attraction> getNearByAttractions(VisitedLocation visitedLocation) {
        List<Attraction> nearbyAttractions = new ArrayList<>();
        for (Attraction attraction : gpsUtil.getAttractions()) {
            if (rewardsService.isWithinAttractionProximity(attraction, visitedLocation.getLocation())) {
                nearbyAttractions.add(attraction);
            }
        }
        return nearbyAttractions;
    }

    private void addShutDownHook() {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            public void run() {
                tracker.stopTracking();
            }
        });
    }

    public void updateUserPreferences(String userName, UserPreferences newPreferences) {
        getUser(userName).setUserPreferences(newPreferences);
    }

    public List<OutputAttraction> the5NearestAttractions(String userName) {

        VisitedLocation visitedLocation = getUser(userName).getLastVisitedLocation();
        List<Attraction> attractions = new ArrayList<>(gpsUtil.getAttractions());
        final Map<Attraction, Double> distanceAttractions = new HashMap<>();
        final Map<Attraction, Double> the5Attractions = new HashMap<>();

        IntStream.range(0, attractions.size()).forEach(i -> {

            double distance = rewardsService.getDistance(visitedLocation.getLocation(), attractions.get(i).getLocation());
            distanceAttractions.put(attractions.get(i), distance);
        });

        List<Map.Entry<Attraction, Double>> distanceOfAttractionList = new ArrayList<>(distanceAttractions.entrySet());
        distanceOfAttractionList.sort(Map.Entry.comparingByValue());
        List<OutputAttraction> outputList = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            OutputAttraction output = new OutputAttraction();
            the5Attractions.put(distanceOfAttractionList.get(i).getKey(), distanceOfAttractionList.get(i).getValue());
            output.setAttractionName(distanceOfAttractionList.get(i).getKey().getAttractionName());
            output.setLongitude(distanceOfAttractionList.get(i).getKey().getLocation().getLongitude());
            output.setLatitude(distanceOfAttractionList.get(i).getKey().getLocation().getLatitude());
            output.setDistance(distanceOfAttractionList.get(i).getValue());
            output.setRewardsPoint(rewardCentralService.getRewardPoints(attractions.get(i).getAttractionId(),getUser(userName).getUserId()));
            outputList.add(i, output);
        }
        return outputList;
    }

    public Map<String, Location> lastUsersLocation() {
        Map<String, Location> lastLocation = new HashMap<>();
        getAllUsers().forEach(user -> {
            VisitedLocation lastVisited = user.getLastVisitedLocation();
            UUID userId = lastVisited.getUserId();
            lastLocation.put(userId.toString(), lastVisited.getLocation());
        });
        return lastLocation;
    }


    /**********************************************************************************
     *
     * Methods Below: For Internal Testing
     *
     **********************************************************************************/
    private static final String tripPricerApiKey = "test-server-api-key";
    // Database connection will be used for external users, but for testing purposes internal users are provided and stored in memory
    private final Map<String, User> internalUserMap = new HashMap<>();

    private void initializeInternalUsers() {
        IntStream.range(0, InternalTestHelper.getInternalUserNumber()).forEach(i -> {
            String userName = "internalUser" + i;
            String phone = "000";
            String email = userName + "@tourGuide.com";
            User user = new User(UUID.randomUUID(), userName, phone, email);
            generateUserLocationHistory(user);

            internalUserMap.put(userName, user);
        });
        logger.debug("Created " + InternalTestHelper.getInternalUserNumber() + " internal test users.");
    }

    private void generateUserLocationHistory(User user) {
        Location location = new Location();
        location.setLatitude(generateRandomLatitude());
        location.setLongitude(generateRandomLongitude());
        IntStream.range(0, 3).forEach(i -> {
            user.addToVisitedLocations(new VisitedLocation(user.getUserId(), location, getRandomTime()));
        });
    }

    public double generateRandomLongitude() {
        double leftLimit = -180;
        double rightLimit = 180;
        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
    }

    public double generateRandomLatitude() {
        double leftLimit = -85.05112878;
        double rightLimit = 85.05112878;
        return leftLimit + new Random().nextDouble() * (rightLimit - leftLimit);
    }

    public Date getRandomTime() {
        LocalDateTime localDateTime = LocalDateTime.now().minusDays(new Random().nextInt(30));
        return Date.from(localDateTime.toInstant(ZoneOffset.UTC));
    }

}