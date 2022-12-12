package tourGuide.service;


import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import tourGuide.newGpsUtil.Attraction;
import tourGuide.newGpsUtil.VisitedLocation;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
public class GpsUtilService {

    public interface GetUserLocation {
        @GET("/getLocation")
        Call<VisitedLocation> userLocation(@Query("userId") UUID userId);
    }

    public interface GetAttractions {
        @GET("/getAttractions")
        Call<List<Attraction>> getAttractions();
    }

    Retrofit tourGuideGPS = new Retrofit.Builder()
            .baseUrl("http://localhost:8081")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();


    public VisitedLocation getUserLocation(UUID userId) {
        GetUserLocation retrofitUserLocation = tourGuideGPS.create(GetUserLocation.class);

        try {
            Call<VisitedLocation> callUserLocationSync = retrofitUserLocation.userLocation(userId);
            return callUserLocationSync.execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public List<Attraction> getAttractions() {
        GetAttractions retrofitAttractions = tourGuideGPS.create(GetAttractions.class);
        try {
            Call<List<Attraction>> callAttractionsSync = retrofitAttractions.getAttractions();
           return callAttractionsSync.execute().body();
        } catch (IOException e) {

            throw new RuntimeException(e);
        }
    }

}

