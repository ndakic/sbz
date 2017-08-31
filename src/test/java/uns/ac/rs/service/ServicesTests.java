package uns.ac.rs.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import uns.ac.rs.model.Article;
import uns.ac.rs.model.enums.StatusOfArticle;
import uns.ac.rs.repository.ArticleRepository;

import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class ServicesTests {

	@Autowired
	ArticleRepository articleRepository;

	@Test
	public void testFindAll(){
		List<Article> articles  = articleRepository.findAll();
		assertThat(articles).isNotNull();
	}

	@Test
	@Transactional
	@Rollback(true)
	public void testAddArticle(){

		Article article = new Article();
		article.setTitle("Toshiba Lap Top");
		article.setPrice(10000.0);
		article.setAmount(10);
		article.setMin(5);
		article.setDate(new Date());
		article.setStatus(StatusOfArticle.ACTIVE);

		int dbSizeBeforeAdd = articleRepository.findAll().size();

		Article articleTest = articleRepository.save(article);
		assertThat(articleTest).isNotNull();

		List<Article> articles = articleRepository.findAll();

		assertThat(articles).hasSize(dbSizeBeforeAdd + 1);
	}
}
