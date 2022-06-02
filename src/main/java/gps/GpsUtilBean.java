package gps;


import gpsUtil.GpsUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
public class GpsUtilBean {

    @Bean
    public GpsUtil gpsUtil(){
        Locale.setDefault(Locale.ENGLISH);
        return new GpsUtil();
    }
}
