package tourGuideGPS.service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.UUID;


@Service
public class GPSService {

    public GPSService() {
        Locale.setDefault(Locale.ENGLISH);
    }

    public VisitedLocation getUserLocation(UUID userId) {
        return new GpsUtil().getUserLocation(userId);
    }

    public List<Attraction> getAttractions() {
        return new GpsUtil().getAttractions();
    }


}
