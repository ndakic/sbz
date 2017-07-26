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
import uns.ac.rs.model.ItemDiscount;
import uns.ac.rs.model.enums.BillStatus;
import uns.ac.rs.repository.ArticleRepository;
import uns.ac.rs.repository.BillRepository;
import uns.ac.rs.repository.UserRepository;

import java.util.ArrayList;
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
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BillRepository billRepository;

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

        KieSession kieSession = kieContainer.newKieSession("bills");
        List<Item> items = bill.getItems();

        bill.setBuyer(userRepository.findOneByUsername(bill.getBuyer().getUsername()));
        bill.setReceivedPoints(bill.getBuyer().getUserProfile().getPoints());

        double currentPrice = 0;
        for(Item item: items){
            item.setBill(bill);
            double itemPrice = item.getPrice() * item.getQuantity();
            currentPrice += itemPrice;
            item.setCurrentPrice(itemPrice);
            item.setItemDiscounts(new ArrayList<ItemDiscount>());

            kieSession.insert(item);
        }
        bill.setCurrentPrice(currentPrice);
        System.out.println("Total: " + currentPrice);

        kieSession.insert(bill);

        kieSession.fireAllRules();
        kieSession.dispose();

        double final_price = 0.0;
        for (Item item: bill.getItems()) {
            double item_price = item.getCurrentPrice();
            List<ItemDiscount> discounts = item.getItemDiscounts();

            // ako nema popusta
            if(discounts.size() == 0){
                final_price += item_price;
                item.setFinalPrice(item_price);
            }

            // ako ima popusta
            double discount_sum = 0;
            for(ItemDiscount discount: discounts){
                double item_price_final = item_price - (item_price * discount.getDiscount() / 100);
                item.setFinalPrice(item_price_final);
                final_price += item_price_final;

                discount_sum += discount.getDiscount();
            }

            // proveriti da li je visina popusta manja od maksimalno dozvoljene
            // ako jeste, postaviti je
            // ako je veca, postaviti maksimalno dozvoljenu

            item.set_discount(discount_sum);
        }

        bill.setFinalPrice(final_price);

        // dodati popuste za racun (drool rools)

        return new ResponseEntity<Bill>(bill, HttpStatus.OK);
    }


    @PostMapping(value = "/submit_bill")
    public ResponseEntity<Bill> submit_bill(@RequestBody Bill bill) throws Exception{

        // postavi ostale podatke racuna > vreme, status itd.
        // izracunati koliko poena korisnik treba da dobije, sacuvati ih
        // skinuti iskoriscenje poene
        // sacuvati racun

        bill.setDate(new Date());
        bill.setStatus(BillStatus.INPROCESS);

        billRepository.save(bill);

        return new ResponseEntity<Bill>(bill, HttpStatus.OK);
    }

}
