package uns.ac.rs.model;

import uns.ac.rs.model.enums.Role;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * Created by Nikola Dakic on 7/5/17.
 */

@Entity
@Table(name="user")
public class User implements Serializable{

    @Id
    private String username;

    @NotNull
    private String password;

    @NotNull
    private Role role;

    @OneToOne(cascade = {CascadeType.ALL})
    private UserProfile userProfile ;

    public User() {
    }

    public User(String username, String password, Role role) {
        this.username = username;
        this.password = password;
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public UserProfile getUserProfile() {
        return userProfile;
    }

    public void setUserProfile(UserProfile userProfile) {
        this.userProfile = userProfile;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", userProfile=" + userProfile +
                '}';
    }
}
