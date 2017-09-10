package uns.ac.rs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uns.ac.rs.model.UserCategory;

/**
 * Created by Nikola Dakic on 7/27/17.
 */

public interface UserCategoriesRepository extends JpaRepository<UserCategory, Long> {

    UserCategory findOneByTitle(String title);

}
