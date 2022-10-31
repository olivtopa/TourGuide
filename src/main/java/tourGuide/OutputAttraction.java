package tourGuide;

import com.sun.javafx.scene.control.skin.DoubleFieldSkin;


import tourGuide.newGpsUtil.Attraction;
import tourGuide.service.RewardsService;
import tourGuide.service.TourGuideService;

public class OutputAttraction {

    Attraction attraction;
    String attractionName;
    Double longitude;
    Double latitude;
    Double distance;
    int rewardsPoint;


    public void setAttractionName(String attractionName) {
        this.attractionName = attractionName;
    }

    public Double getLongitudee() {
        return longitude;
    }

    public void setLongitudee(Double longitudee) {
        this.longitude = longitudee;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getDistance() {
        return distance;
    }

    public void setDistance(Double distance) {
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
                ", longitudee=" + longitude +
                ", latitude=" + latitude +
                ", distance=" + distance +
                ", rewardsPoint=" + rewardsPoint +
                '}';
    }
}
