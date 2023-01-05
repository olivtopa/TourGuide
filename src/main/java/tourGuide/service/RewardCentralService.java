package tourGuide.service;

import org.springframework.stereotype.Service;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

import java.io.IOException;
import java.util.UUID;

@Service
public class RewardCentralService {

    public interface GetRewardPoints {
        @GET("/getRewardPoints")
        Call <Integer> rewardPoints (@Query("attractionId") UUID attractionId, @Query("userID") UUID userId);
    }

    Retrofit rewardRetrofit = new Retrofit.Builder()
            .baseUrl("http://localhost:8083")
            .addConverterFactory(JacksonConverterFactory.create())
            .build();

    public int getRewardPoints(UUID attractionID, UUID userId) {
        GetRewardPoints retrofitRewardPoints = rewardRetrofit.create(GetRewardPoints.class);

        try {
            Call<Integer> callRewardPoints = retrofitRewardPoints.rewardPoints(attractionID,userId);
            return callRewardPoints.execute().body();
        } catch (IOException e) {
            throw  new RuntimeException(e);
        }
    }
}
