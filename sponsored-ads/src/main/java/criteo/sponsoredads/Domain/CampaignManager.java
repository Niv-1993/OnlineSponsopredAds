package criteo.sponsoredads.Domain;

import criteo.sponsoredads.DataAccess.Entities.DalProduct;
import criteo.sponsoredads.DataAccess.Entities.DalPromotedProduct;
import criteo.sponsoredads.DataAccess.Services.DalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;
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
        validateInput(name, startDate, productIds, bid);
        List<DalProduct> existingProducts = dalService.getProductsById(productIds);
        var dalCreatedCampaign = dalService.createCampaign(name, startDate, existingProducts, bid);
        var campaign = new Campaign(dalCreatedCampaign.getId(), dalCreatedCampaign.getName(),
                dalCreatedCampaign.getStartDate(), bid, productIds);
        log.info(String.format("Successfully created new campaign. %s", campaign));
        return campaign;
    }

    public Product serveAd(String category) {
        if (category == null) {
            log.error("Category name is null or empty");
            throw new InvalidParameterException("Campaign name cannot be null or empty");
        }
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


    private void validateInput(String name, LocalDate startDate, List<Integer> productIds, double bid) {
        log.info("Validating input of new campaign creation");
        if (name == null || name.trim().isEmpty()) {
            log.error(String.format("Campaign name is null or empty. Got: %s", name));
            throw new InvalidParameterException("Campaign name cannot be null or empty");
        }
        if (startDate == null || startDate.isBefore(LocalDate.now())) {
            log.error(String.format("Start date is in the past or null. Got: %s", startDate));
            throw new InvalidParameterException("Start date must be in the future");
        }
        if (bid <= 0) {
            log.error(String.format("Invalid bid value. %f must be > 0", bid));
            throw new InvalidParameterException("Bid must be greater than zero");
        }
        if (productIds == null) {
            log.error("Products ids list is null.");
            throw new InvalidParameterException("Product Ids cannot be null");
        }
    }
}
