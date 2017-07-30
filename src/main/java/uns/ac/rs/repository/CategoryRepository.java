package uns.ac.rs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uns.ac.rs.model.ArticleCategory;

/**
 * Created by Nikola Dakic on 7/20/17.
 */
public interface CategoryRepository extends JpaRepository<ArticleCategory, Long> {

    ArticleCategory findOneByTitle(String title);

}
