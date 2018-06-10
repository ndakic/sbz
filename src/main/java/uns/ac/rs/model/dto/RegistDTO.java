package uns.ac.rs.model.dto;

import lombok.Data;
import uns.ac.rs.model.UserProfile;
import uns.ac.rs.model.ValidPassword;
import uns.ac.rs.model.enums.Role;

/**
 * Created by daka on 5/15/18.
 */

@Data
public class RegistDTO {

    private String username;

    @ValidPassword
    private String password;

    private Role role;

    private UserProfile userProfile;

    public RegistDTO() {
    }

    public RegistDTO(String username, String password) {
        this.username = username;
        this.password = password;
    }


}
