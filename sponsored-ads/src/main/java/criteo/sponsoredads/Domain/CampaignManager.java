package criteo.sponsoredads.Domain;

import criteo.sponsoredads.DataAccess.Entities.DalProduct;
import criteo.sponsoredads.DataAccess.Entities.DalPromotedProduct;
import criteo.sponsoredads.DataAccess.Services.DalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.time.LocalDate;
import java.util.List;

@Component
@Slf4j
public class CampaignManager {

    private final DalService dalService;

    @Autowired
    public CampaignManager(DalService dalService) {
        this.dalService = dalService;
    }

    public Campaign createCampaign(String name, LocalDate startDate, List<Integer> productIds, double bid) {
        List<DalProduct> existingProducts = dalService.getProductsById(productIds);
        var dalCreatedCampaign = dalService.createCampaign(name, startDate, existingProducts, bid);
        var campaign = new Campaign(dalCreatedCampaign.getId(), dalCreatedCampaign.getName(),
                dalCreatedCampaign.getStartDate(), bid, productIds);
        log.info(String.format("Successfully created new campaign. %s", campaign));
        return campaign;
    }

    public Product serveAd(String category) {
        DalPromotedProduct dalPromotedProduct = dalService.getHighestBidProductByCategory(category);
        var promotedProduct = new Product(dalPromotedProduct.getId().getProductId(),
                dalPromotedProduct.getProduct().getTitle(), dalPromotedProduct.getProduct().getPrice(),
                dalPromotedProduct.getProduct().getSerialNumber(),
                dalPromotedProduct.getCategory().getName(),
                dalPromotedProduct.getCampaign().getName());
        log.info(String.format("Successfully got promoted product with highest bid on active campaign. %s",
                promotedProduct));
        return promotedProduct;
    }
}
