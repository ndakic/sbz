package uns.ac.rs.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Nikola Dakic on 7/22/17.
 */

@Entity
@Table(name = "spending_limit")
public class SpendingLimit implements Serializable {

    private static final long serialVersionUID = 7252000032978851163L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Double upperRange;

    private Double lowerRange;

    private Double percent;


    // getters and setters

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getUpperRange() {
        return upperRange;
    }

    public void setUpperRange(Double upperRange) {
        this.upperRange = upperRange;
    }

    public Double getLowerRange() {
        return lowerRange;
    }

    public void setLowerRange(Double lowerRange) {
        this.lowerRange = lowerRange;
    }

    public Double getPercent() {
        return percent;
    }

    public void setPercent(Double percent) {
        this.percent = percent;
    }
}
