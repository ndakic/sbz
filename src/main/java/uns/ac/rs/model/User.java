package uns.ac.rs.model;

import uns.ac.rs.model.enums.Role;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
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

    private String token;

    public User() {
    }

    public User(String username, String password, Role role, String token) {
        this.username = username;
        this.password = password;
        this.role = role;
        this.token = token;
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                ", token='" + token + '\'' +
                '}';
    }
}
