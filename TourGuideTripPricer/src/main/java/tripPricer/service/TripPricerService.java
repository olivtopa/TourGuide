package tripPricer.service;

import org.springframework.stereotype.Service;
import tripPricer.Provider;
import tripPricer.TripPricer;

import java.util.List;
import java.util.UUID;



@Service
public class TripPricerService {
    TripPricer tripPricer = new TripPricer();
    public List<Provider> getPrices(String apiKey, UUID attractionId,int adult,int children,int nightsStays,int rewardsPoints){

        return tripPricer.getPrice(apiKey, attractionId,adult,children,nightsStays,rewardsPoints);
    }
    public String providerName(String apiKey, int adults) {
        return tripPricer.getProviderName(apiKey,adults);
    }
}
