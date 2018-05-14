package uns.ac.rs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uns.ac.rs.model.User;

/**
 * Created by Nikola Dakic on 7/5/17.
 */
public interface UserRepository extends JpaRepository<User, Long>{

    User findOneByUsernameAndPassword(String username, String password);

    User findOneByUsername(String username);

}
