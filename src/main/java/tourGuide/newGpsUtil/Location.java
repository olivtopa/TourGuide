package tourGuide.newGpsUtil;

import tourGuide.newGpsUtil.Attraction;
import  tourGuide.newGpsUtil.Location;
import tourGuide.newGpsUtil.VisitedLocation;

public class Location {

    public final double longitude;
    public final double latitude;

    public Location(double latitude, double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }
}
