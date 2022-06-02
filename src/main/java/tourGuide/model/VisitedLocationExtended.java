package tourGuide.model;

import java.util.Date;
import java.util.UUID;

public class VisitedLocationExtended {
    public UUID userId;
    public LocationExtended location;
    public Date timeVisited;

    public VisitedLocationExtended(UUID userId, LocationExtended givenLocation, Date timeVisited) {
        userId = userId;
        location = location;
        timeVisited = timeVisited;
    }

    public VisitedLocationExtended() {
    }
}
