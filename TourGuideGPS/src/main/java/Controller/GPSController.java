package Controller;

import java.util.concurrent.ExecutionException;
import gpsUtil.location.VisitedLocation;

@RestController
public class GPSController {

    @RequestMapping("/getLocation")
    public String getLocation(@RequestParam String userName) throws ExecutionException, InterruptedException {
        VisitedLocation visitedLocation = tourGuideService.getUserLocation(getUser(userName));
        return JsonStream.serialize(visitedLocation.location);
    }

    @RequestMapping("/getNearbyAttractions")
    public String getNearbyAttractions(@RequestParam String userName){
        return JsonStream.serialize(tourGuideService.the5NearestAttractions(userName));
    }
}
