package tourGuide;

import tourGuide.newGpsUtil.Attraction;

public class OutputAttraction {

    private Attraction attraction;
    private String attractionName;
    private Double longitude;
    private Double latitude;
    private Double distance;
    private int rewardsPoint;

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

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
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
                ", longitude=" + longitude +
                ", latitude=" + latitude +
                ", distance=" + distance +
                ", rewardsPoint=" + rewardsPoint +
                '}';
    }
}
