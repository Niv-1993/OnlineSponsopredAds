package criteo.sponsoredads.DataAccess.Services;

import criteo.sponsoredads.DataAccess.Entities.CompositeKeys.PromotedProductKey;
import criteo.sponsoredads.DataAccess.Entities.*;
import criteo.sponsoredads.DataAccess.Repositories.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class DalService {
    private final DalPromotedProductRepository dalPromotedProductRepository;
    private final DalCampaignRepository dalCampaignRepository;
    private final DalProductsRepository dalProductsRepository;

    @Autowired
    public DalService(DalPromotedProductRepository dalProductRepository,
                      DalCampaignRepository dalCampaignRepository,
                      DalProductsRepository dalProductsRepository) {
        this.dalPromotedProductRepository = dalProductRepository;
        this.dalCampaignRepository = dalCampaignRepository;
        this.dalProductsRepository = dalProductsRepository;
    }

    @Transactional
    public DalCampaign createCampaign(String name, LocalDate startDate, List<DalProduct> products, double bid) {
        try {
            DalCampaign savedCampaign = initializeCampaign(name,startDate, bid);

            List<DalPromotedProduct> promotedProducts = getDalPromotedProducts(savedCampaign, products);
            List<DalPromotedProduct> savedPromotedProducts = dalPromotedProductRepository.saveAll(promotedProducts);
            var promotedProductSet = new HashSet<>(savedPromotedProducts);
            savedCampaign.setPromotedProducts(promotedProductSet);
            dalCampaignRepository.save(savedCampaign);
            return savedCampaign;

        } catch (Exception e) {
            log.error("Error creating campaign: {}", e.getMessage());
            throw new RuntimeException("Error creating campaign", e);
        }
    }

    public List<DalProduct> getProductsById(List<Integer> productIds) {
        List<DalProduct> products = dalProductsRepository.findByIdIn(productIds);

        if (products.size() != productIds.size()) {
            List<Integer> retrievedProductIds = products.stream().map(DalProduct::getId).toList();
            List<Integer> invalidIds = productIds.stream()
                    .filter(id -> !retrievedProductIds.contains(id)).toList();
            log.error("Invalid product IDs: " + invalidIds);
            throw new IllegalArgumentException("Invalid product IDs: " + invalidIds);
        }
        return products;
    }

    private DalCampaign initializeCampaign(String name, LocalDate startDate, double bid) {
        DalCampaign dalCampaign = new DalCampaign();
        dalCampaign.setName(name);
        dalCampaign.setStartDate(startDate);
        dalCampaign.setBid(bid);
        return dalCampaignRepository.saveAndFlush(dalCampaign);
    }

    private List<DalPromotedProduct> getDalPromotedProducts(DalCampaign campaign, List<DalProduct> products) {
        return products.stream().map(dalProduct -> createPromotedProduct(campaign, dalProduct))
                .collect(Collectors.toList());
    }

    private DalPromotedProduct createPromotedProduct(DalCampaign campaign, DalProduct dalProduct) {
        var id = new PromotedProductKey();
        id.setCampaignId(campaign.getId());
        id.setProductId(dalProduct.getId());

        var promotedProduct = new DalPromotedProduct();
        promotedProduct.setId(id);
        promotedProduct.setCampaign(campaign);
        promotedProduct.setProduct(dalProduct);
        promotedProduct.setCategory(dalProduct.getCategory()); //TODO: maybe not needed

        return promotedProduct;
    }


}
