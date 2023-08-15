package criteo.sponsoredads.DataAccess;

import criteo.sponsoredads.DataAccess.Entities.DalCategory;
import criteo.sponsoredads.DataAccess.Entities.DalProduct;
import criteo.sponsoredads.DataAccess.Repositories.DalCategoryRepository;
import criteo.sponsoredads.DataAccess.Repositories.DalProductsRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Random;

@Component
public class DataLoader {

    private final DalProductsRepository dalProductsRepository;
    private final DalCategoryRepository dalCategoryRepository;

    @Autowired
    public DataLoader(DalProductsRepository dalProductsRepository, DalCategoryRepository dalCategoryRepository) {
        this.dalProductsRepository = dalProductsRepository;
        this.dalCategoryRepository = dalCategoryRepository;
    }


    @PostConstruct
    private void populateDatabase() {
        String[] categories = {"Electronics", "Fashion", "Books", "HomeNKitchen", "Toys", "HealthNBeauty"};

        String[][] products = {
                {"Smartphone", "12345678", "599.99"},
                {"Laptop", "98765432", "999.99"},
                {"T-shirt", "11223344", "14.99"},
                {"Novel", "55667788", "7.99"},
                {"Blender", "22334455", "49.99"},
                {"Action Figure", "33445566", "29.99"},
                {"Shampoo", "77889900", "6.99"}
        };
        var dalCategories = new ArrayList<DalCategory>();
        for (String category : categories) {
            DalCategory dalCategory = new DalCategory();
            dalCategory.setName(category);
            var saved = dalCategoryRepository.save(dalCategory);
            dalCategories.add(saved);
        }
        Random random = new Random();
        for (String[] product : products) {
            var dalProduct = new DalProduct();
            dalProduct.setTitle(product[0]);
            dalProduct.setSerialNumber(product[1]);
            dalProduct.setPrice(Double.parseDouble(product[2]));
            int randomIndex = random.nextInt(dalCategories.size());
            dalProduct.setCategory(dalCategories.get(randomIndex));
            dalProductsRepository.save(dalProduct);
        }
    }
}
