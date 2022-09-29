package tourGuide.newGpsUtil;

import java.util.UUID;

    public class Attraction {
        private String attractionName;
        private String city;
        private String state;
        private UUID attractionId;
        private double longitude ;
        private double latitude ;

        public String getAttractionName() {
            return attractionName;
        }
        public void setAttractionName(String attractionName) {
            this.attractionName = attractionName;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getState() {
            return state;
        }

        public void setState(String state) {
            this.state = state;
        }

        public UUID getAttractionId() {
            return attractionId;
        }

        public void setAttractionId(UUID attractionId) {
            this.attractionId = attractionId;
        }

        public Location getLocation() {
            Location location = new Location();
            location.setLongitude(longitude);
            location.setLatitude(latitude);
            return location;
        }

    }
