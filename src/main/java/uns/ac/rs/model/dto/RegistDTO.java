package uns.ac.rs.model.dto;

import uns.ac.rs.model.ValidPassword;

/**
 * Created by daka on 5/15/18.
 */
public class RegistDTO {

    private String username;

    @ValidPassword
    private String password;

    public RegistDTO() {
    }

    public RegistDTO(String username, String password) {
        this.username = username;
        this.password = password;
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

    @Override
    public String toString() {
        return "RegistDTO{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
