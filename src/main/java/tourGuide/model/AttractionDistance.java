package tourGuide.model;

import gpsUtil.location.Attraction;
import gpsUtil.location.Location;
import tourGuide.service.RewardsService;

public class AttractionDistance extends AttractionExtended implements Comparable<AttractionDistance> {

    private LocationExtended fromLocation;
    public AttractionDistance(LocationExtended fromLocation,AttractionExtended toAttraction) {
        super(toAttraction.id, toAttraction.name, toAttraction.city, toAttraction.state, toAttraction.latitude, toAttraction.longitude);
        this.fromLocation = fromLocation;
    }

    @Override
    public int compareTo(AttractionDistance that) {
        if (this.fromLocation.latitude != that.fromLocation.latitude || this.fromLocation.longitude != that.fromLocation.longitude){
            throw  new RuntimeException("Choose a different origin");

        }
        double distanceToThis = this.fromLocation.getDistance(new LocationExtended(this.latitude, this.longitude));
        double distanceToThat = that.fromLocation.getDistance(new LocationExtended(that.latitude, that.longitude));
        return Double.valueOf(distanceToThis).compareTo(Double.valueOf(distanceToThat));
    }

}
