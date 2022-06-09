package tourGuide.service;

import com.google.common.util.concurrent.RateLimiter;
import gpsUtil.GpsUtil;
import gpsUtil.location.VisitedLocation;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;

public class GpsUtilExtended extends GpsUtil {
    private static final RateLimiter rateLimiter = RateLimiter.create(1000.0D);

    public VisitedLocation getUserLocationExtd(UUID userId){
        rateLimiter.acquire();
        this.sleep();

        // TODO

        return null;


    }


    private void sleep() {
        int random = ThreadLocalRandom.current().nextInt(30, 100);

        try {
            TimeUnit.MILLISECONDS.sleep((long)random);
        } catch (InterruptedException var3) {
        }

    }
}
