package tourGuide.api;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import tourGuide.model.ProposalForm;
import tourGuide.model.ProviderExtended;

import java.util.List;

@FeignClient(name="trip", url="http://trip:8083")
public interface TripClient {

    @GetMapping("/calculateProposals")
    List<ProviderExtended> calculateProposals(@RequestBody ProposalForm proposalForm);

}
