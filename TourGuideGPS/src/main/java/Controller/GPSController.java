package Controller;

import java.util.concurrent.ExecutionException;

import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tourGuide.service.TourGuideService;

@RestController
public class GPSController {

    @RequestMapping("/getLocation")
    public Location getLocation(@RequestParam String userName) throws ExecutionException, InterruptedException {
        VisitedLocation visitedLocation = tourGuideService.getUserLocation(getUser(userName));
        return visitedLocation.location;
    }

    @RequestMapping("/getNearbyAttractions")
    /* model */public Object getNearbyAttractions(@RequestParam String userName){
        return tourGuideService.the5NearestAttractions(userName);
    }
}

