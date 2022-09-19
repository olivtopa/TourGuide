package tourGuide;

import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import org.zalando.jackson.datatype.money.MoneyModule;
import tourGuide.service.TourGuideService;
import tourGuide.user.User;
import tourGuide.user.UserPreferences;
import tripPricer.Provider;

@RestController
public class TourGuideController {
    private static Logger logger = LoggerFactory.getLogger(TourGuideController.class);

	@Autowired
	TourGuideService tourGuideService;
    	
    @RequestMapping("/")
    public String index() {
        return "Greetings from TourGuide!";
    }
    
    @RequestMapping("/getLocation") 
    public Location getLocation(@RequestParam String userName) throws ExecutionException, InterruptedException {
    	VisitedLocation visitedLocation = tourGuideService.getUserLocation(getUser(userName));
		return visitedLocation.location;
    }
    
    //  TODO: Change this method to no longer return a List of Attractions.
 	//  Instead: Get the closest five tourist attractions to the user - no matter how far away they are.
 	//  Return a new JSON object that contains:
    	// Name of Tourist attraction, 
        // Tourist attractions lat/long, 
        // The user's location lat/long, 
        // The distance in miles between the user's location and each of the attractions.
        // The reward points for visiting each Attraction.
        //    Note: Attraction reward points can be gathered from RewardsCentral
    @RequestMapping("/getNearbyAttractions") 
    public
    List<tourGuide.OutputAttraction> getNearbyAttractions(@RequestParam String userName){
        return tourGuideService.the5NearestAttractions(userName);
    }
    
    @RequestMapping("/getRewards") 
    public List<tourGuide.user.UserReward> getRewards(@RequestParam String userName) {
    	return tourGuideService.getUserRewards(getUser(userName));
    }
    
    @RequestMapping("/getAllCurrentLocations")
    public Map<String, Location> getAllCurrentLocations() {
    	// TODO: Get a list of every user's most recent location as JSON
    	//- Note: does not use gpsUtil to query for their current location, 
    	//        but rather gathers the user's current location from their stored location history.
    	//
    	// Return object should be the just a JSON mapping of userId to Locations similar to:
    	//     {
    	//        "019b04a9-067a-4c76-8817-ee75088c3822": {"longitude":-48.188821,"latitude":74.84371} 
    	//        ...
    	//     }
    		return tourGuideService.lastUsersLocation();
    }

    @RequestMapping("/getTripDeals")
    public List<Provider> getTripDeals(@RequestParam String userName) {
    	List<Provider> providers = tourGuideService.getTripDeals(getUser(userName));
    	return providers;
    }
    
    private User getUser(String userName) {
    	return tourGuideService.getUser(userName);
    }

    @PutMapping(value = "/userPreferences")
    public void updateUserPreferences(@RequestBody UserPreferences newPreferences, @RequestParam String userName){
        logger.info("PUT Request to modify users preferences");
        tourGuideService.updateUserPreferences(userName,newPreferences);
        logger.info("new preferences : lowerPricePoint : {}, numberOfAdults : {}",newPreferences.getLowerPricePoint(),newPreferences.getNumberOfAdults());
    }

}