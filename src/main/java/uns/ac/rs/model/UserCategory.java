package uns.ac.rs.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikola Dakic on 7/22/17.
 */

@Entity
@Table(name = "user_category")
public class UserCategory {

    private static final long serialVersionUID = 5065800290418172364L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String title;

    @ManyToMany
    private List<SpendingLimit> limits = new ArrayList<SpendingLimit>();


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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<SpendingLimit> getLimits() {
        return limits;
    }

    public void setLimits(List<SpendingLimit> limits) {
        this.limits = limits;
    }
}
