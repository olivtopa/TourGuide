package tourGuide.newGpsUtil;


import org.springframework.beans.factory.annotation.Autowired;
import tourGuide.service.TourGuideService;

public class Location {

    @Autowired TourGuideService tourGuideService;

    public double longitude = tourGuideService.generateRandomLongitude();
    public double latitude = tourGuideService.generateRandomLatitude();

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
