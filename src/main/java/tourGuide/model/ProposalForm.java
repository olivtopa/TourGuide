package tourGuide.model;

/*Informations trip*/

import tourGuide.user.User;

import java.util.List;

    public class ProposalForm {
        public User user;
        public List<AttractionNearBy> attractions;
        public int cumulativeRewardPoints;

        public ProposalForm(User user, List<AttractionNearBy> attractions, int cumulativeRewardPoints) {
            this.user = user;
            this.attractions = attractions;
            this.cumulativeRewardPoints = cumulativeRewardPoints;
        }

        public ProposalForm() {
        }


}
