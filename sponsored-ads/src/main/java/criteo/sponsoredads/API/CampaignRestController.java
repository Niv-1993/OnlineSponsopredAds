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
            @NotEmpty(message = "Products ids cannot be empty")
            List<Integer> productIds) {}
    private record ProductRecord(String title, String category, double price, String serialNumber, String campaign) {}
    private final CampaignManager campaignManager;

    @Autowired
    public CampaignRestController(CampaignManager campaignManager) {
        this.campaignManager = campaignManager;
    }


    @PostMapping("create")
    public ResponseEntity<Object> createCampaign(@RequestBody @Valid CampaignRecord body) {
        Campaign newCampaign = campaignManager.createCampaign(body.name(), body.date(), body.productIds(), body.bid());
        return new ResponseEntity<>(generateRecord(newCampaign), HttpStatus.CREATED);
    }

    @GetMapping("serveAd")
    public ResponseEntity<Object> serveAd(@RequestParam(name = "category") String category) {
        Product product = campaignManager.serveAd(category);
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
