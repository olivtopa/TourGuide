package tourGuide.service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.springframework.stereotype.Service;
import retrofit2.Retrofit;

import java.util.List;
import java.util.UUID;

@Service
public class GpsUtilService {

   Retrofit retrofit = new Retrofit.Builder()
           .baseUrl("http://localhost:8081/getLocation/")
           .build();

    public VisitedLocation getUserLocation(UUID userId) {
        return new GpsUtil().getUserLocation(userId);
    }

    public List<Attraction> getAttractions(){
        return new GpsUtil().getAttractions();
    }
}
