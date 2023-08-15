package criteo.sponsoredads.DataAccess.Entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "campaigns")
public class DalCampaign {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private int id;

    @Column(name = "start_date" ,columnDefinition = "DATE", nullable = false)
    private LocalDate startDate;

    @Column(name = "bid")
    private double bid;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "campaign")
    private Set<DalPromotedProduct> promotedProducts;

}
