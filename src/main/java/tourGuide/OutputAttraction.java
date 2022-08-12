package tourGuide;

import com.sun.javafx.scene.control.skin.DoubleFieldSkin;
import gpsUtil.GpsUtil;
import gpsUtil.location.Attraction;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;

public class OutputAttraction {

    Attraction attraction ;
    String attractionName ;
    Double longitudee ;
    Double latitude ;
    Long distance ;
    int rewardsPoint;



    public Attraction getAttraction() {
        return attraction;
    }

    public void setAttraction(Attraction attraction) {
        this.attraction = attraction;
    }

    public String getAttractionName() {
        return attractionName;
    }

    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    public Double getLongitudee() {
        return longitudee;
    }

    public void setLongitudee(Double longitudee) {
        this.longitudee = longitudee;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Long getDistance() {
        return distance;
    }

    public void setDistance(Long distance) {
        this.distance = distance;
    }

    public int getRewardsPoint() {
        return rewardsPoint;
    }

    public void setRewardsPoint(int rewardsPoint) {
        this.rewardsPoint = rewardsPoint;
    }

    @Override
    public String toString() {
        return "OutputAttraction{" +
                "attraction=" + attraction +
                ", attractionName='" + attractionName + '\'' +
                ", longitudee=" + longitudee +
                ", latitude=" + latitude +
                ", distance=" + distance +
                ", rewardsPoint=" + rewardsPoint +
                '}';
    }
}
