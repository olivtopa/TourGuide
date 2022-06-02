package tourGuide.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import tourGuide.model.AttractionNearBy;
import tourGuide.model.ProposalForm;
import tourGuide.model.ProviderExtended;
import tourGuide.user.User;

import java.util.List;

public class TripRequestServiceImpl implements TripRequestService{

    private Logger logger = LoggerFactory.getLogger(TripRequestServiceImpl.class);

    private final TripClient tripClient;
    @Autowired
    private ObjectMapper objectMapper;

    public TripRequestServiceImpl(TripClient tripClient) {
        this.tripClient = tripClient;
    }

    @Override
    public List<ProviderExtended> calculateProposals(User user, List<AttractionNearBy> attractions, int cumulativeRewardPoints) {
            ProposalForm proposalForm = new ProposalForm(user, attractions, cumulativeRewardPoints);
            List<ProviderExtended> proposals = tripClient.calculateProposals(proposalForm);
            return proposals;
        }


}
