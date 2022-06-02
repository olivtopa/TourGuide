package trip;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TripController<TripService> {

    private Logger logger = LoggerFactory.getLogger(TripController.class);
    @Autowired private TripService tripService;


}
