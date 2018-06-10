package uns.ac.rs.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikola Dakic on 7/22/17.
 */

@Entity
@Data
@Table(name = "user_category")
public class UserCategory implements Serializable {

    private static final long serialVersionUID = 5065800290418172364L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String title;

    @ManyToMany(cascade = {CascadeType.ALL})
    private List<SpendingLimit> limits = new ArrayList<SpendingLimit>();

    public UserCategory() {
    }

    public UserCategory(String title, List<SpendingLimit> limits) {
        this.title = title;
        this.limits = limits;
    }


}
