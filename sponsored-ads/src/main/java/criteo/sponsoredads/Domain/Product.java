package criteo.sponsoredads.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class Product {
    private int id;
    private String title;
    private double price;
    private String serialNumber;
    private String category;
    private String campaign;

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", price=" + price +
                ", serialNumber='" + serialNumber + '\'' +
                ", category='" + category + '\'' +
                ", campaign='" + campaign + '\'' +
                '}';
    }
}
