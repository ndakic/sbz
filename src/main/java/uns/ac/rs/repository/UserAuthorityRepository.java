package uns.ac.rs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uns.ac.rs.model.UserAuthority;

/**
 * Created by daka on 5/4/18.
 */
public interface UserAuthorityRepository extends JpaRepository<UserAuthority, Long> {
}
