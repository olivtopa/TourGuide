package tourGuide;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tourGuide.api.TourGuideService;
import tourGuide.api.UserService;
import tourGuide.model.ProviderExtended;
import tourGuide.model.VisitedLocationExtended;

import java.util.List;

/**
 * API class for tourguide methods
 */
@RestController
public class TourGuideController {

    @Autowired private TourGuideService tourGuideService;
    @Autowired private UserService userService;
    @Autowired private ObjectMapper objectMapper;

    @GetMapping("/")
    public String welcome() {
        return "Welcome at the TourGuide application !";
    }

    /**
     * Gets the last know visited location for a given user. Based on user visited location history.
     * @param userName the name of the user whose last location is requested.
     * @return String JSON formatted user last location.
     */
    @GetMapping("/getLastLocation")
    public String getLastLocation(@RequestParam String userName) throws JsonProcessingException {
        VisitedLocationExtended visitedLocation = tourGuideService.getLastUserLocation(userService.getUser(userName));
        return objectMapper.writeValueAsString(visitedLocation.location);
    }

    /**
     * Gets the 5 attractions which are the closest to the user's location, regardless of their distance from him.
     * @param userName the name of the user to be considered for the research.
     * @return String JSON formatted attraction nearby list (id, name, attraction location, user location, distance, reward points).
     */
    @GetMapping("/getNearbyAttractions")
    public String getNearbyAttractions(@RequestParam String userName) throws JsonProcessingException {
        return objectMapper.writeValueAsString(tourGuideService.getNearByAttractions(userName));
    }

    /**
     * Gets the list of all rewards for a given user.
     * @param userName the name of the user whose reward list is requested.
     * @return String JSON formatted reward list.
     */
    @GetMapping("/getRewards")
    public String getRewards(@RequestParam String userName) throws JsonProcessingException {
        return objectMapper.writeValueAsString(tourGuideService.getUserRewards(userService.getUser(userName)));
    }

    /**
     * Gets the last know visited location for all users. Based on user visited location history.
     * @return String JSON formatted map with user name as key and last location as value.
     */
    @GetMapping("/getAllLastLocations")
    public String getAllLastLocations() throws JsonProcessingException {
        return objectMapper.writeValueAsString(tourGuideService.getLastLocationAllUsers());
    }

    /**
     * Gets the proposed trips for a given user. Based on user preferences.
     * @return String JSON formatted list of providers (name, price and id).
     */
    @GetMapping("/getTripDeals")
    public String getTripDeals(@RequestParam String userName) throws JsonProcessingException {
        List<ProviderExtended> providers = tourGuideService.getTripDeals(userService.getUser(userName));
        return objectMapper.writeValueAsString(providers);
    }
   

}