package tripPricer.service;

import org.springframework.stereotype.Service;
import tripPricer.Provider;
import tripPricer.TripPricer;

import java.util.List;
import java.util.UUID;



@Service
public class TripPricerService {
  private List<Provider> prices;
    private String tripName;
    TripPricer tripPricer = new TripPricer();
  public List<Provider> getPrices(String apiKey, UUID attractionId,int adult,int children,int nightsStays,int rewardsPoints){

   tripPricer.getPrice(apiKey, attractionId,adult,children,nightsStays,rewardsPoints);
  return prices;
 }

 public String providerName(String apiKey, int adults) {

      tripPricer.getProviderName(apiKey,adults);
      return tripName;
 }
}
