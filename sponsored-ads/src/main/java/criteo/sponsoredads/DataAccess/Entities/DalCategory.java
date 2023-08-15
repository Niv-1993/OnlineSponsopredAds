package criteo.sponsoredads.DataAccess.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "categories")
public class DalCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "category_name")
    private String name;

    @OneToMany(mappedBy = "category")
    private Set<DalProduct> product;

    @OneToMany(mappedBy = "category")
    private Set<DalPromotedProduct> promotedProducts;  // TODO: maybe not needed
}