package criteo.sponsoredads.API;

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
@RequestMapping(path = "api/")
public class CampaignRestController {
    private final CampaignManager campaignManager;

    @Autowired
    public CampaignRestController(CampaignManager campaignManager) {
        this.campaignManager = campaignManager;
    }

    public record CampaignRecord(String name, LocalDate date, List<Integer> productIds, double bid){}

    @PostMapping("createCampaign")
    public ResponseEntity<Object> createCampaign(@RequestBody CampaignRecord body) {
        var x = campaignManager.createCampaign(body.name(), body.date(), body.productIds(), body.bid());
        return new ResponseEntity<>(x, HttpStatus.CREATED);
    }
}
