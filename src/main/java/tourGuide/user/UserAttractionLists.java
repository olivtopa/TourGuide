package tourGuide.user;

import tourGuide.model.AttractionExtended;

import java.util.List;

public class UserAttractionLists {

    public List<AttractionExtended> attractionList;
    public  List<User> userList;

    public  UserAttractionLists(List<AttractionExtended>attractions, List<User> userList) {
            this.attractionList = attractions;
            this.userList = userList;

    }

    public UserAttractionLists(){
        }
}
