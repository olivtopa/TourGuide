package tourGuide.service;


import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;
import tourGuide.NewTripPricer.Provider;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

;

@Service
public class TripPricerService {

    public interface GetPrice {
        @GET("/getPrice")
        Call<List<Provider>> getPrice(@Query("apiKey") String apiKey, @Query("attractionId") UUID attractionId,
                                      @Query("adults") int adults, @Query("children") int children,
                                      @Query("nightsStay") int nightsStay, @Query("rewardPoints") int rewardPoints);
    }

    public interface GetProviderName {
        @GET("/getProviderName")
        Call<List<Provider>> getProviderName(@Query("apiKey") String apiKey, @Query("adults") int adults);
    }

    Retrofit retrofitTripPricer = new Retrofit.Builder()
            .baseUrl("http://localhost:8084")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    public List<Provider> getPrice(String apiKey, UUID attractionID, int adults, int children, int nightStay, int rewardPoint) {
        GetPrice retrofitProviderPrice = retrofitTripPricer.create(GetPrice.class);

        try {
            Call<List<Provider>> callProviderListPrice = retrofitProviderPrice.getPrice(apiKey, attractionID, adults, children, nightStay, rewardPoint);
            return callProviderListPrice.execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public List<Provider> getProviderName(String apiKey, int adults) {
        GetProviderName retrofitProviderName = retrofitTripPricer.create(GetProviderName.class);

        try {
            Call<List<Provider>> callProviderListName = retrofitProviderName.getProviderName(apiKey, adults);
            return callProviderListName.execute().body();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
