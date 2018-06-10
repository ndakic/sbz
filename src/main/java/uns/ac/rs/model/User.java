package uns.ac.rs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import uns.ac.rs.model.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Nikola Dakic on 7/5/17.
 */

@Entity
@Data
@Table(name="user")
public class User implements Serializable{

    @Id
    @Column(unique = true)
    private String username;

    @NotNull
    private String password;

    @NotNull
    private Role role;

    @OneToOne(cascade = CascadeType.ALL)
    private UserProfile userProfile ;

    private String firstName;

    private String lastName;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @JsonIgnore
    @OneToMany(mappedBy = "user_id")
    private List<UserAuthority> userAuthorities;

    public User() {
    }

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }


}
