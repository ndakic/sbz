package uns.ac.rs.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uns.ac.rs.model.Article;

/**
 * Created by Nikola Dakic on 7/19/17.
 */
public interface ArticleRepository extends JpaRepository<Article, Long> {


}
