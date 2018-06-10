package uns.ac.rs.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Nikola Dakic on 7/22/17.
 */

@Entity
@Data
@Table(name = "user_profile")
public class UserProfile implements Serializable {

    private static final long serialVersionUID = -6657778871516103498L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String address;

    private Double points;

    @ManyToOne(cascade = {CascadeType.ALL})
    private UserCategory userCategory;

    public UserProfile() {
    }

    public UserProfile(String address, Double points, UserCategory userCategory) {
        this.address = address;
        this.points = points;
        this.userCategory = userCategory;
    }

}
