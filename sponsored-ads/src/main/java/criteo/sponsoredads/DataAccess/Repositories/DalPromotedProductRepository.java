package criteo.sponsoredads.DataAccess.Repositories;

import criteo.sponsoredads.DataAccess.Entities.CompositeKeys.PromotedProductKey;
import criteo.sponsoredads.DataAccess.Entities.DalPromotedProduct;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DalPromotedProductRepository extends JpaRepository<DalPromotedProduct, PromotedProductKey> {
}
