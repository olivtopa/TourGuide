package gps;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class GpsController {

    private Logger logger = LoggerFactory.getLogger(GpsController.class);
    @Autowired
    private GpsService gpsService;
}
