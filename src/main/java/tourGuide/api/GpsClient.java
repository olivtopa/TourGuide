package tourGuide.api;

import gpsUtil.location.VisitedLocation;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import tourGuide.model.AttractionExtended;
import tourGuide.model.VisitedLocationExtended;
import tourGuide.user.User;

import java.util.List;

@FeignClient(name="gps",url="8081")
public interface GpsClient {

    @PatchMapping("/trackAllUserLocations")
    public List<User> trackAllUserLocations(@RequestBody List<User> userList);

    @GetMapping("/getAllAttractions")
    public List<AttractionExtended> getAllAttractions();

    @GetMapping("GetCurrentUserLocation")
    public VisitedLocationExtended getCurrentUserLocation(@RequestParam("userId") String userId);


}
