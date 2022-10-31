package tourGuideGPS.Controller;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import tourGuideGPS.service.GPSService;


@RestController
public class GPSController {

    @Autowired
    GPSService gpsService;

    @RequestMapping("/getLocation")
    public VisitedLocation getLocation(@RequestParam UUID userId) throws ExecutionException, InterruptedException {
        return gpsService.getUserLocation(userId);
    }

    @RequestMapping("/getAttractions")
    public List<Attraction> getAttractions() {
        return gpsService.getAttractions();
    }
}

