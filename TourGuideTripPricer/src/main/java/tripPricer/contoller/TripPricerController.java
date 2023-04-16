package tripPricer.contoller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import tripPricer.Provider;
import tripPricer.service.TripPricerService;


import java.util.List;
import java.util.UUID;

@RestController
public class TripPricerController {

    @Autowired TripPricerService tripPricerService;

    @RequestMapping("/getPrice")
    public List<Provider> getPrices (@RequestParam String apiKey, @RequestParam UUID attractionId,
                                    @RequestParam int adults, @RequestParam int children, @RequestParam int nightsStay,
                                     @RequestParam int rewardPoints){
        return tripPricerService.getPrices(apiKey, attractionId, adults, children, nightsStay, rewardPoints);
    }

    @RequestMapping("/getProviderName")
    public String getProviderName (@RequestParam String apiKey, @RequestParam int adults){
        return tripPricerService.providerName(apiKey,adults);
    }

}
