package tourGuide.user;

import tourGuide.model.AttractionExtended;

public class UserAttraction {

    public  User user;
    public AttractionExtended attraction;

    public UserAttraction(User user, AttractionExtended attraction){
        this.user = user;
        this.attraction = attraction;
    }
    public UserAttraction() {

    }

}
