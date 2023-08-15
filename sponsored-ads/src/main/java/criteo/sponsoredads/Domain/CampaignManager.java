package criteo.sponsoredads.Domain;

import criteo.sponsoredads.DataAccess.Entities.DalProduct;
import criteo.sponsoredads.DataAccess.Services.DalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class CampaignManager {

    private final DalService dalService;

    @Autowired
    public CampaignManager(DalService dalService) {
        this.dalService = dalService;
    }

    public Campaign createCampaign(String name, LocalDate startDate, List<Integer> productIds, double bid) {
        List<DalProduct> validProducts = dalService.validateProductIds(productIds);
        var dalCreatedCampaign = dalService.createCampaign(name, startDate, validProducts, bid);
        return new Campaign(dalCreatedCampaign.getId(),
                dalCreatedCampaign.getName(),
                dalCreatedCampaign.getStartDate(),
                bid,
                productIds);
    }
}
