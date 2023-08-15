package criteo.sponsoredads.API;

import criteo.sponsoredads.Domain.Campaign;
import criteo.sponsoredads.Domain.CampaignManager;
import criteo.sponsoredads.Domain.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/campaign")
public class CampaignRestController {
    private record CampaignRecord(String name, LocalDate date, double bid, List<Integer> productIds) {
    }

    private record ProductRecord(String title, String category, double price, String serialNumber, String campaign) {
    }

    private final CampaignManager campaignManager;

    @Autowired
    public CampaignRestController(CampaignManager campaignManager) {
        this.campaignManager = campaignManager;
    }


    @PostMapping("create")
    public ResponseEntity<Object> createCampaign(@RequestBody CampaignRecord body) {
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
