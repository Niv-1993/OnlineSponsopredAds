package criteo.sponsoredads.DataAccess.Entities;

import criteo.sponsoredads.DataAccess.Entities.CompositeKeys.PromotedProductKey;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "promoted_products")
public class DalPromotedProduct {
    @EmbeddedId
    private PromotedProductKey id;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("productId")
    private DalProduct product;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("campaignId")
    private DalCampaign campaign;

    @ManyToOne(fetch = FetchType.LAZY) // TODO: maybe not needed
    private DalCategory category;
}
