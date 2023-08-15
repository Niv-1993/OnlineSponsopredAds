package criteo.sponsoredads.DataAccess.Repositories;

import criteo.sponsoredads.DataAccess.Entities.DalProduct;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DalProductsRepository extends JpaRepository<DalProduct,Integer> {
    List<DalProduct> findByIdIn(List<Integer> productIds);
}
