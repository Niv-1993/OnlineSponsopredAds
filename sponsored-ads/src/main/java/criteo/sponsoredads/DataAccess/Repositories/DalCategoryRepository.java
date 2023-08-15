package criteo.sponsoredads.DataAccess.Repositories;

import criteo.sponsoredads.DataAccess.Entities.DalCategory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DalCategoryRepository extends JpaRepository<DalCategory, Integer> {
}
