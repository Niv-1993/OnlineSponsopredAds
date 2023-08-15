package criteo.sponsoredads.API;

import criteo.sponsoredads.Domain.Campaign;
import criteo.sponsoredads.Domain.CampaignManager;
import criteo.sponsoredads.Domain.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/campaign")
public class CampaignRestController {
    private record CampaignRecord(
            @NotNull(message = "Name cannot be null")
            @NotEmpty(message = "Name cannot be empty")
            String name,
            @NotNull(message = "Date cannot be null")
            LocalDate date,
            @Min(value = 0, message = "bid should be positive")
            double bid,
            @NotNull(message = "Products ids cannot be null")
            @NotEmpty(message = "Products ids cannot be empty") // assuming a campaign needs to have at lease 1 product
            List<Integer> productIds) {}
    private record ProductRecord(String title, String category, double price, String serialNumber, String campaign) {}
    private final CampaignManager campaignManager;

    @Autowired
    public CampaignRestController(CampaignManager campaignManager) {
        this.campaignManager = campaignManager;
    }


    /**
     * Endpoint to create a new campaign.
     *
     * <p>
     * This endpoint allows sellers to create new campaigns for promoting their products.
     * Each campaign will contain shared properties such as start-date, bid, and a group of
     * products to promote.
     * </p>
     *
     * @param body The request payload containing campaign details. This should include:
     * <ul>
     *     <li>name - The name of the campaign.</li>
     *     <li>date - The start date of the campaign.</li>
     *     <li>productIds - A list of product identifiers to promote in this campaign.</li>
     *     <li>bid - The price the seller is willing to pay for a click on a product advertised in this campaign.</li>
     * </ul>
     *
     * @return A {@link ResponseEntity} containing:
     * <ul>
     *     <li>The created campaign details if the request was successful.</li>
     *     <li>An appropriate error response if the request failed.</li>
     * </ul>
     */
    @PostMapping("create")
    public ResponseEntity<Object> createCampaign(@RequestBody @Valid CampaignRecord body) {
        Campaign newCampaign = campaignManager.createCampaign(body.name(), body.date(), body.productIds(), body.bid());
        return new ResponseEntity<>(generateRecord(newCampaign), HttpStatus.CREATED);
    }


    /**
     * Endpoint to retrieve ads based on the specified category.
     *
     * <p>
     * This endpoint provides clients with a promoted product from active campaigns based on
     * the provided category. The product with the highest bid is prioritized. If no
     * product matches the specified category, the product with the overall highest bid
     * is returned.
     * </p>
     *
     * @param category The name of the product category for which ads are requested. This
     *                 parameter determines the type of products the client is interested in.
     *                 The category is case-insensitive.
     *
     * @return A {@link ResponseEntity} containing:
     * <ul>
     *     <li>The details of the promoted product if a match is found.</li>
     *     <li>An appropriate error response if no active campaigns or products match the criteria.</li>
     * </ul>
     */
    @GetMapping("serveAd")
    public ResponseEntity<Object> serveAd(@RequestParam(name = "category") String category) {
        Product product = campaignManager.serveAd(category.toLowerCase());
        return new ResponseEntity<>(generateRecord(product), HttpStatus.OK);
    }

    private CampaignRecord generateRecord(Campaign campaign) {
        return new CampaignRecord(campaign.getName(),
                campaign.getStartDate(),
                campaign.getBid(),
                campaign.getPromotedProducts());
    }

    private ProductRecord generateRecord(Product product) {
        return new ProductRecord(product.getTitle(),
                product.getCategory(),
                product.getPrice(),
                product.getSerialNumber(),
                product.getCampaign());
    }
}
