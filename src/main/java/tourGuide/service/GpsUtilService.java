package tourGuide.service;

import gpsUtil.GpsUtil;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.http.GET;
import retrofit2.http.Path;
import tourGuide.TourGuideController;
import tourGuide.newGpsUtil.Attraction;
import tourGuide.newGpsUtil.Location;
import tourGuide.newGpsUtil.VisitedLocation;

import java.util.List;
import java.util.UUID;

@Service
public class GpsUtilService {
    String userName;
    public interface GetUserLocation {
        @GET("/getLocation {userName}")
        Call<Location> userLocation(@Path("userName") String userName);
}

    public interface GetAttraction {
        @GET("/getAttractions")
        Call<List<Attraction>> getAttractions();
}
   Retrofit tourGuideGPS = new Retrofit.Builder()
           .baseUrl("http://localhost:8081")
           .build();
//Retrofit for UserLocation
    GetUserLocation retrofitUserLocation = tourGuideGPS.create(GetUserLocation.class);
    Call<Location> callUserLocationSync = retrofitUserLocation.userLocation(userName);
//Retrofit for Attractions
    GetAttraction retrofitAttractions = tourGuideGPS.create(GetAttraction.class);
    Call<List<Attraction>> callAttractionsSync = retrofitAttractions.getAttractions();



}

