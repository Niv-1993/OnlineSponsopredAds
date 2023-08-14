package criteo.sponsoredads.DataAccess.Entities.CompositeKeys;

import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
@Getter
@Setter
public class PromotedProductKey implements Serializable {
    private int productId;
    private int campaignId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PromotedProductKey that = (PromotedProductKey) o;
        return productId == that.productId && campaignId == that.campaignId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(productId, campaignId);
    }
}
