package criteo.sponsoredads.DataAccess.Repositories;

import criteo.sponsoredads.DataAccess.Entities.DalCampaign;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DalCampaignRepository extends JpaRepository<DalCampaign, Integer> {
}
