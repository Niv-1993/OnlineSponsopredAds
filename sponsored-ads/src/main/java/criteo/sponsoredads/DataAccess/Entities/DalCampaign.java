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

    @Column(name = "start_date", columnDefinition = "DATE", nullable = false)
    private LocalDate startDate; // for simplicity - taking the date only (without precise time)

    @Column(name = "end_date", columnDefinition = "DATE", nullable = false)
    private LocalDate endDate; // added for simple query on fetching active campaigns

    @Column(name = "bid")
    private double bid;

    @Column(name = "name", nullable = false)
    private String name;

    @OneToMany(mappedBy = "campaign", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<DalPromotedProduct> promotedProducts;

}
