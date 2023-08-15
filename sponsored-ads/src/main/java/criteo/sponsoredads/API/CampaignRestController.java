package criteo.sponsoredads.API;

import criteo.sponsoredads.Domain.Campaign;
import criteo.sponsoredads.Domain.CampaignManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(path = "api/v1/campaign")
public class CampaignRestController {
    private record CampaignRecord(String name, LocalDate date, double bid, List<Integer> productIds) {}
    private final CampaignManager campaignManager;

    @Autowired
    public CampaignRestController(CampaignManager campaignManager) {
        this.campaignManager = campaignManager;
    }


    @PostMapping("create")
    public ResponseEntity<Object> createCampaign(@RequestBody CampaignRecord body) {
        var newCampaign = campaignManager.createCampaign(body.name(), body.date(), body.productIds(), body.bid());
        return new ResponseEntity<>(generateRecord(newCampaign), HttpStatus.CREATED);
    }

    private CampaignRecord generateRecord(Campaign campaign) {
        return new CampaignRecord(campaign.getName(),
                campaign.getStartDate(),
                campaign.getBid(),
                campaign.getPromotedProducts());
    }
}
