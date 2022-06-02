package tourGuide.model;

import gpsUtil.location.Attraction;

import java.util.UUID;

public class AttractionExtended extends LocationExtended {

    public String name;
    public String city;
    public String state;
    public UUID id;

    public AttractionExtended(double lat, double lon) {
        super(lat, lon);
    }
     public AttractionExtended(UUID id, String name, String city, String state, double latitude, double longitude) {

        this.id = id;
        this.name=name;
        this.city=city;
        this.state=state;
        this.latitude=latitude;
        this.longitude=longitude;
    }

    public AttractionExtended() {
    }
}
