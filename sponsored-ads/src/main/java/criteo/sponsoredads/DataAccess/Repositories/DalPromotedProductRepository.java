package criteo.sponsoredads.DataAccess.Repositories;

import criteo.sponsoredads.DataAccess.Entities.CompositeKeys.PromotedProductKey;
import criteo.sponsoredads.DataAccess.Entities.DalPromotedProduct;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
@Repository
public interface DalPromotedProductRepository extends JpaRepository<DalPromotedProduct, PromotedProductKey> {
    @Query("SELECT pp FROM DalPromotedProduct pp " +
            "JOIN pp.campaign c " +
            "JOIN pp.category cat " +
            "WHERE cat.name = ?1 " +
            "AND CURRENT_DATE BETWEEN c.startDate AND c.endDate " +
            "ORDER BY c.bid DESC")
    Page<DalPromotedProduct> findTopByCategoryNameAndHighestBid(String categoryName,Pageable pageable);

    @Query("SELECT pp FROM DalPromotedProduct pp " +
            "JOIN pp.campaign c " +
            "ORDER BY c.bid DESC")
    Page<DalPromotedProduct> findTopByHighestBid(Pageable pageable);
}
