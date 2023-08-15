package criteo.sponsoredads.Domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class Campaign {
    private int id;
    private String name;
    private LocalDate startDate;
    private double bid;
    private List<Integer> promotedProducts;

    @Override
    public String toString() {
        return "Campaign{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", startDate=" + startDate +
                ", bid=" + bid +
                ", promotedProducts=" + promotedProducts +
                '}';
    }
}
