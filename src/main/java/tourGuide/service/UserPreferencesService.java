package tourGuide.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tourGuide.user.UserPreferences;

@Service
public class UserPreferencesService {

    public int nbChildren;
    public int nbAdult;
    public int tripDuration;
    public int ticketQuantity;

    @Autowired
    UserPreferences userPreferences;

    public void update(UserPreferences userPreferences) {
        userPreferences.setNumberOfChildren(nbChildren);
        userPreferences.setNumberOfAdults(nbAdult);
        userPreferences.setTripDuration(tripDuration);
        userPreferences.setTicketQuantity(ticketQuantity);
    }
}
