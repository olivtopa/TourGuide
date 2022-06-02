package tourGuide.api;

import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import tourGuide.model.AttractionExtended;
import tourGuide.model.VisitedLocationExtended;
import tourGuide.user.User;

import java.util.List;


public interface GpsRequestService {
    List<User> trackAllUserLocations(List<User> userList);

    List<AttractionExtended> getAllAttractions();

    VisitedLocationExtended getCurrentUserLocation(User user);
}
