package tourGuide.service;

import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import gpsUtil.location.VisitedLocation;
import org.springframework.stereotype.Service;

//import com.squareup.retrofit2;


import java.util.List;
import java.util.UUID;

@Service
public class GpsUtilService {

  /*  Retrofit retrofit = new Retrofit.builder()
            .baseUrl("http://localhost:8081/getLocation/")
            .build();

   */

    public VisitedLocation getUserLocation(UUID userId) {
        return new GpsUtil().getUserLocation(userId);
    }

    public List<Attraction> getAttractions(){
        return new GpsUtil().getAttractions();
    }
}
