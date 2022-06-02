package gps;

import gpsUtil.GpsUtil;
import gpsUtil.location.VisitedLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tourGuide.model.AttractionExtended;
import tourGuide.model.LocationExtended;
import tourGuide.model.VisitedLocationExtended;
import tourGuide.user.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;

public class GpsServiceImpl implements GpsService {

    private static final int NUMBER_OF_EXPECTED_USER_PARTITIONS = 10;
    private static final int THREAD_POOL_SIZE = NUMBER_OF_EXPECTED_USER_PARTITIONS * 2;

    private Logger logger = LoggerFactory.getLogger(GpsServiceImpl.class);
    @Autowired
    private GpsUtil gpsUtil;

    // For performance reasons it is required to split users for submission on several threads
    private List<List<User>> divideUserList(List<User> userList) {
        List<List<User>> partitionList = new LinkedList<List<User>>();
        int expectedSize = userList.size() / NUMBER_OF_EXPECTED_USER_PARTITIONS;
        if (expectedSize == 0) {
            partitionList.add(userList);
            return partitionList;
        }
        for (int i = 0; i < userList.size(); i += expectedSize) {
            partitionList.add(userList.subList(i, Math.min(i + expectedSize, userList.size())));
        }
        return partitionList;
    }

    @Override
    public List<User> trackAllUserLocations(List<User> userList) {
        logger.debug("trackAllUserLocations with list of size = " + userList.size());
        // The number of threads has been defined after several tests to match the performance target
        ForkJoinPool forkJoinPool = new ForkJoinPool(THREAD_POOL_SIZE);
        // Divide user list into several parts and submit work separately for these parts
        divideUserList(userList).stream().parallel().forEach( partition -> {
            try {
                logger.debug("trackAllUserLocations submits calculation for user partition of size" +  partition.size());
                forkJoinPool.submit( () -> partition.stream().parallel().forEach(user -> {
                    VisitedLocation visitedLocation = gpsUtil.getUserLocation(user.getUserId());
                    user.addToVisitedLocations(newVisitedLocationData(visitedLocation));
                })).get();
            } catch (InterruptedException | ExecutionException e) {
                logger.error("trackAllUserLocations got an exception");
                e.printStackTrace();
                throw new RuntimeException("trackAllUserLocations got an exception");
            }
        });
        forkJoinPool.shutdown();
        return userList;
    }

    /*Get current user location */

    @Override
    public VisitedLocationExtended getCurrentUserLocation(String userIdString) {
        logger.debug("getUserLocation with userId = " + userIdString);
        UUID userId = UUID.fromString(userIdString);
        return newVisitedLocationData(gpsUtil.getUserLocation(userId));
    }

    @Override
    public List<AttractionExtended> getAllAttractions() {
        logger.debug("getAllAttractions");
        List<AttractionExtended> dataList = new ArrayList<AttractionExtended>();
        gpsUtil.getAttractions().stream().forEach(attraction -> {
            AttractionExtended data = new AttractionExtended();
            data.id = attraction.attractionId;
            data.name = attraction.attractionName;
            data.city = attraction.city;
            data.state = attraction.state;
            data.latitude = attraction.latitude;
            data.longitude = attraction.longitude;
            dataList.add(data);
        });
        return dataList;
    }

    @Override
    public VisitedLocationExtended newVisitedLocationData(VisitedLocation visitedLocation) {
        return new VisitedLocationExtended(visitedLocation.userId,
                new LocationExtended(visitedLocation.location.latitude, visitedLocation.location.longitude),
                visitedLocation.timeVisited);
    }

}
