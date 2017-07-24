package uns.ac.rs.controller;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uns.ac.rs.model.Article;
import uns.ac.rs.model.Bill;
import uns.ac.rs.model.Item;
import uns.ac.rs.repository.ArticleRepository;

import java.util.List;
import java.util.Optional;

/**
 * Created by Nikola Dakic on 7/20/17.
 */

@RestController
@RequestMapping("/api/article")
public class ArticleController {

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private KieContainer kieContainer;

    @PostMapping(value = "/add")
    public ResponseEntity<Article> add(@RequestBody Article article) throws Exception{
        Article art = articleRepository.save(article);
        return Optional.ofNullable(art)
                .map(result -> new ResponseEntity<>(art, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping(value = "/all")
    public List<Article> getAll(){
        return articleRepository.findAll();
    }

    @PostMapping(value = "/bill")
    public ResponseEntity<Bill> bill(@RequestBody Bill bill) throws Exception{

        System.out.println(bill.toString());

        KieSession kieSession = kieContainer.newKieSession("bills");
        List<Item> items = bill.getItems();

        double total = 0;
        for(Item item: items){
            double sum = item.getPrice() * item.getQuantity();
            total += sum;
        }

        bill.setCurrentPrice(total);
        System.out.println("Total: " + total);

        kieSession.insert(bill);

        kieSession.fireAllRules();
        kieSession.dispose();


        return new ResponseEntity<Bill>(bill, HttpStatus.OK);
    }

}
