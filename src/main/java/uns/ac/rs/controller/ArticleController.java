package uns.ac.rs.controller;



import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.config.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uns.ac.rs.model.Article;
import uns.ac.rs.model.Bill;
import uns.ac.rs.model.enums.BillStatus;
import uns.ac.rs.security.TokenUtils;
import uns.ac.rs.service.ArticleService;
import uns.ac.rs.service.BillService;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Date;
import java.util.InvalidPropertiesFormatException;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private TokenUtils tokenUtils;

    private static final Logger logger = LogManager.getLogger(ArticleController.class);


    @GetMapping(value = "/{id}")
    public ResponseEntity<Article> getArticleByID(@PathVariable Long id) throws Exception{
        Article art =  articleService.getArticle(id);

        if(art == null){

            String authToken = request.getHeader("authorization");
            String username = tokenUtils.getUsernameFromToken(authToken);

            logger.warn("WARNING! Article doesn't exist but still searched by user: " + username);

            Article art_empty = new Article();
            art_empty.setId(-1L);
            return new ResponseEntity<Article>(art_empty, HttpStatus.FORBIDDEN);

        }

        return new ResponseEntity<Article>(art, HttpStatus.OK);
    }

    @PostMapping(value = "/add")
    public ResponseEntity<Article> add(@Valid @RequestBody Article article) throws Exception{
        Article art = articleService.addArticle(article);
        return Optional.ofNullable(art)
                .map(result -> new ResponseEntity<>(art, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/all")
    public List<Article> getAll() throws Exception{

        return articleService.getArticles();
    }

//    @PreAuthorize("hasAuthority('test')")
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

    @GetMapping(value = "/search/{title}")
    public List<Article> search_articles(@PathVariable String title) throws Exception{

        Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+$");
        Matcher matcher = pattern.matcher(title);

        if(!matcher.matches())
            throw new Exception("Not Allowed!");

        return articleService.findAllbyTitle(title);
    }
}
