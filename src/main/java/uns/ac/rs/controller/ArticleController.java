package uns.ac.rs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uns.ac.rs.model.Article;
import uns.ac.rs.model.Bill;
import uns.ac.rs.model.enums.BillStatus;
import uns.ac.rs.service.ArticleService;
import uns.ac.rs.service.BillService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * Created by Nikola Dakic on 7/20/17.
 */

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @Autowired
    private BillService billService;

    @GetMapping(value = "/{id}")
    public Article getArticleByID(@PathVariable Long id) throws Exception{
        return articleService.getArticle(id);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Article> add(@RequestBody Article article) throws Exception{
        Article art = articleService.addArticle(article);
        return Optional.ofNullable(art)
                .map(result -> new ResponseEntity<>(art, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/all")
    public List<Article> getAll() throws Exception{
        return articleService.getArticles();
    }

    @PostMapping(value = "/bill")
    public ResponseEntity<Bill> bill(@RequestBody Bill b) throws Exception{

        Bill bill = articleService.calculateDiscounts(b);

        return new ResponseEntity<Bill>(bill, HttpStatus.OK);
    }

    @PostMapping(value = "/submit_bill")
    public ResponseEntity<Bill> submit_bill(@RequestBody Bill b) throws Exception{

        b.setDate(new Date());
        b.setStatus(BillStatus.INPROCESS);

        Bill bill = billService.saveBIll(b);

        return new ResponseEntity<Bill>(bill, HttpStatus.OK);
    }

    @GetMapping(value = "/orders")
    public List<Article> orders() throws Exception{
        return articleService.orders();
    }

    @PostMapping(value = "/order_more")
    public ResponseEntity<Article> order_more(@RequestBody Article article) throws Exception{

        Article art = articleService.orderArticles(article);

        return Optional.ofNullable(art)
                .map(result -> new ResponseEntity<>(art, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

}
