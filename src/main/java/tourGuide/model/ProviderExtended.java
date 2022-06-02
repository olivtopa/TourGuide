package tourGuide.model;

import java.util.UUID;

public class ProviderExtended {

    public String name;
    public double price;
    public UUID tripId;

    public ProviderExtended(String attractionName, double attractionPrice, UUID idTrip) {
        name = attractionName;
        price = attractionPrice;
        tripId = idTrip;
    }

    public ProviderExtended() {
    }



}
