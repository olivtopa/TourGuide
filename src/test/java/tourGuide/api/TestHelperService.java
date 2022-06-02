package tourGuide.api;

import gpsUtil.location.Location;
import gpsUtil.location.VisitedLocation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tourGuide.model.AttractionExtended;
import tourGuide.model.LocationExtended;
import tourGuide.model.VisitedLocationExtended;
import tourGuide.user.User;
import tourGuide.user.UserPreferences;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.mockito.Mockito.when;

@Service
public class TestHelperService {

    public final static int NUMBER_OF_TEST_ATTRACTIONS = TourGuideService.NUMBER_OF_PROPOSED_ATTRACTIONS * 2;
    public final static double LATITUDE_USER_ONE = 0.21;
    public final static double LONGITUDE_USER_ONE = -0.00022;
    public final static double LATITUDE_ATTRACTION_ONE = 0.31;
    public final static double LONGITUDE_ATTRACTION_ONE = -0.00032;
    public final static double CURRENT_LATITUDE = 0.111;
    public final static double CURRENT_LONGITUDE = -0.222;

    @Autowired
    GpsRequestService gpsRequest;
    @Autowired
    UserService userService;

    public User generateUser(int index) {
        return new User(new UUID(11 * index, 12 * index), "name" + index, "phone" + index, "email" + index);
    }

    public VisitedLocationExtended generateVisitedLocation(UUID userId, int index) {
        LocationExtended location = new LocationExtended(LATITUDE_USER_ONE * index, LONGITUDE_USER_ONE * index);
        VisitedLocationExtended visitedLocation = new VisitedLocationExtended(userId, location, new Date(index));
        return visitedLocation;
    }

    public List<User> mockGetUsersAndGetCurrentLocations(int numberOfUsers) {
        List<User> givenUsers = new ArrayList<User>();
        for (int i = 0; i < numberOfUsers; i++) {
            givenUsers.add(mockGetUserAndGetCurrentUserLocation(i + 1, null));
        }
        when(userService.getAllUsers()).thenReturn(givenUsers);
        return givenUsers;
    }

    public User mockGetUserAndGetCurrentUserLocation(int index, UserPreferences userPreferences) {
        User user = mockGetUserCurrentAndVisitedLocation(index, userPreferences);
        when(userService.getUser(user.getUserName())).thenReturn(user);
        when(gpsRequest.getCurrentUserLocation(user)).thenReturn(user.getLastVisitedLocation());
        return user;
    }

    public User mockGetCurrentUserLocation(int index) {
        User user = generateUser(index);
        VisitedLocationExtended visitedLocation = generateVisitedLocation(user.getUserId(), index);
        when(gpsRequest.getCurrentUserLocation(user)).thenReturn(visitedLocation);
        return user;
    }

    public User mockGetUserCurrentAndVisitedLocation(int index, UserPreferences userPreferences) {
        User user = mockGetUserCurrentLocation(index);
        UserPreferences preferences = user.getUserPreferences();
        preferences = userPreferences;
        VisitedLocationExtended visitedLocation = generateVisitedLocation(user.getUserId(), index);
        user.addToVisitedLocations(visitedLocation);
        return user;
    }

    public User mockGetUserCurrentLocation(int index) {
        User user = generateUser(index);
        LocationExtended currentLocation = new LocationExtended(CURRENT_LATITUDE, CURRENT_LONGITUDE);
        VisitedLocationExtended visitedLocation = new VisitedLocationExtended(user.getUserId(), currentLocation, new Date());
        when(gpsRequest.getCurrentUserLocation(user)).thenReturn(visitedLocation);
        return user;
    }

    public List<AttractionExtended> mockGetAllAttractions() {
        return mockGetAllAttractions(NUMBER_OF_TEST_ATTRACTIONS);
    }

    public List<AttractionExtended> mockGetAllAttractions(int numberOfTestAttractions) {
        List<AttractionExtended> attractions = new ArrayList<>();
        for (int i = 0; i < numberOfTestAttractions; i++) {
            int index = numberOfTestAttractions - i;
            AttractionExtended attraction = new AttractionExtended(new UUID(index, index + 7000), "name" + index, "city" + index, "state" + index,
                    LATITUDE_ATTRACTION_ONE * index, LONGITUDE_ATTRACTION_ONE * index);
            attractions.add(attraction);
        }
        when(gpsRequest.getAllAttractions()).thenReturn(attractions);
        return attractions;
    }

}
