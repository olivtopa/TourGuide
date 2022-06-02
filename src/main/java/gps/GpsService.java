package gps;


import gpsUtil.location.VisitedLocation;
import org.springframework.stereotype.Service;
import tourGuide.model.AttractionExtended;
import tourGuide.model.VisitedLocationExtended;
import tourGuide.user.User;

import java.util.List;

@Service
public interface GpsService {

    List<User> trackAllUserLocations(List<User> userList);

    VisitedLocationExtended getCurrentUserLocation(String userIdString);

    List<AttractionExtended> getAllAttractions();

    VisitedLocationExtended newVisitedLocationData(VisitedLocation visitedLocation);
}
