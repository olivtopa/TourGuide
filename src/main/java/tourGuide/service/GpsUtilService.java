package tourGuide.service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;


import java.util.List;
import java.util.UUID;

public class GpsUtilService {

    public VisitedLocation getUserLocation(UUID userId) {
        return new GpsUtil().getUserLocation(userId);
    }

    public List<Attraction> getAttractions(){
        return new GpsUtil().getAttractions();
    }
}
