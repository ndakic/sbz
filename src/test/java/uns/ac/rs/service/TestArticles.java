package uns.ac.rs.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import uns.ac.rs.model.*;
import uns.ac.rs.model.enums.Role;
import uns.ac.rs.model.enums.StatusOfArticle;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Nikola Dakic on 9/6/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class TestArticles {

    @Autowired
    ArticleService articleService;

    @Test
    @Transactional
    @Rollback(true)
    public void testAddArticle() throws Exception{

        Article article = new Article();
        article.setTitle("Toshiba Lap Top");
        article.setPrice(10000.0);
        article.setAmount(10);
        article.setMin(5);
        article.setDate(new Date());
        article.setStatus(StatusOfArticle.ACTIVE);

        int dbSizeBeforeAdd = articleService.getArticles().size();

        Article articleTest = articleService.addArticle(article);
        assertThat(articleTest).isNotNull();

        List<Article> articles = articleService.getArticles();

        assertThat(articles).hasSize(dbSizeBeforeAdd + 1);
    }

    @Test
    @Transactional
    @Rollback(true)
    public void testCalculateDiscounts() throws Exception{

        Article article = new Article();
        article.setTitle("MacBook Pro");

        ArticleCategory articleCategory = new ArticleCategory();
        articleCategory.setTitle("cosmetics");
        articleCategory.setDiscount(10.0);

        ArticleCategory superCategory = new ArticleCategory();
        superCategory.setTitle("broad_consumption");
        superCategory.setDiscount(15.0);

        articleCategory.setArticleCategory(superCategory);

        article.setArticleCategory(articleCategory);
        article.setPrice(10000.0);
        article.setAmount(30);
        article.setMin(5);
        article.setOrderStatus(false);

        Item item = new Item();
        item.setArticle(article);
        item.setPrice(article.getPrice());
        item.setQuantity(5);

        List<Item> items = new ArrayList<>();
        items.add(item);

        Bill bill = new Bill();
        bill.setItems(items);
        bill.setBuyer(new User("daka1", "123", Role.customer));

        Bill b = articleService.calculateDiscounts(bill);

        assertThat(b.getFinalPrice()).isNotNull();
        System.out.println("final price: " + b.getFinalPrice());

    }

    @Test
    @Transactional
    @Rollback(true)
    public void testOrderArticles() throws Exception{

        Article article = new Article();
        article.setTitle("MacBook Pro");

        ArticleCategory articleCategory = new ArticleCategory();
        articleCategory.setTitle("cosmetics");
        articleCategory.setDiscount(10.0);

        ArticleCategory superCategory = new ArticleCategory();
        superCategory.setTitle("broad_consumption");
        superCategory.setDiscount(15.0);

        articleCategory.setArticleCategory(superCategory);

        article.setArticleCategory(articleCategory);
        article.setPrice(10000.0);
        article.setAmount(30);
        article.setMin(5);
        article.setOrderStatus(true);

        article.setOrderQuantity(41); // one article more

        Article art = articleService.orderArticles(article);

        System.out.println("amount: " + art.getAmount());

        assertThat(art.getAmount().compareTo(art.getAmount() + art.getMin()));


    }


}
