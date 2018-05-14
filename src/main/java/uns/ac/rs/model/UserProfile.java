package uns.ac.rs.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Nikola Dakic on 7/22/17.
 */

@Entity
@Table(name = "user_profile")
public class UserProfile implements Serializable {

    private static final long serialVersionUID = -6657778871516103498L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String address;

    private Double points;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    private UserCategory userCategory;

    public UserProfile() {
    }

    public UserProfile(String address, Double points, UserCategory userCategory) {
        this.address = address;
        this.points = points;
        this.userCategory = userCategory;
    }

    public void addPoints(Double p){
        this.points = this.points + p;
    }

    public void minusPoints(Double p){
        this.points = this.points - p;
    }

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Double getPoints() {
        return points;
    }

    public void setPoints(Double points) {
        this.points = points;
    }

    public UserCategory getUserCategory() {
        return userCategory;
    }

    public void setUserCategory(UserCategory userCategory) {
        this.userCategory = userCategory;
    }

    @Override
    public String toString() {
        return "UserProfile{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", points=" + points +
                ", userCategory=" + userCategory +
                '}';
    }
}
