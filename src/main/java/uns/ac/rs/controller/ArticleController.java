package uns.ac.rs.controller;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uns.ac.rs.model.*;
import uns.ac.rs.model.enums.BillStatus;
import uns.ac.rs.repository.ArticleRepository;
import uns.ac.rs.repository.BillRepository;
import uns.ac.rs.repository.UserRepository;

import java.util.*;

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

        KieSession kieSession = kieContainer.newKieSession("articles");

        bill.setDate(new Date());

        List<Item> items = bill.getItems();
        List<Bill> bills = billRepository.findAll();

        AllBills allBills = new AllBills(bills);
        kieSession.insert(allBills);

        bill.setBillDiscounts(new ArrayList<BillDiscount>());
        bill.setBuyer(userRepository.findOneByUsername(bill.getBuyer().getUsername()));

        double currentPrice = 0;
        for(Item item: items){
            item.setBill(bill);
            double itemPrice = item.getPrice() * item.getQuantity();
            currentPrice += itemPrice;
            item.setCurrentPrice(itemPrice);
            item.setItemDiscounts(new ArrayList<ItemDiscount>());
            System.out.println(item.getBill().getId());
            kieSession.insert(item);
        }
        bill.setCurrentPrice(currentPrice);

        System.out.println(bill.getDate());

        for(Item item: items){
            System.out.println(item.getOrder());
        }

        kieSession.insert(bill);

        kieSession.fireAllRules();
        kieSession.dispose();

        // items discounts
        double final_price = 0.0;
        for (Item item: bill.getItems()) {
            double item_price = item.getCurrentPrice();
            List<ItemDiscount> discounts = item.getItemDiscounts();

            // if doesn't exist
            if(discounts.size() == 0){
                final_price += item_price;
                item.setFinalPrice(item_price);
            }

            HashSet<Double> dis = new HashSet<>();

            for(ItemDiscount itemDiscount: discounts){
                dis.add(itemDiscount.getDiscount());
            }

            double discount_sum = 0;

            for (Double d : dis) {
                discount_sum += d;
            }

            // if exist

            for(ItemDiscount discount: discounts){
                double item_price_final = item_price - (item_price * discount_sum / 100);
                item.setFinalPrice(item_price_final);
                final_price += item_price_final;
            }

            // proveriti da li je visina popusta manja od maksimalno dozvoljene
            // ako jeste, postaviti je
            // ako je veca, postaviti maksimalno dozvoljenu
            // ispraviti racunanje popusta kad budem dodatne radio, sabrati ih sve prvo pa tek onda primeniti popust


            item.set_discount(discount_sum);
        }


        KieSession kieSession2 = kieContainer.newKieSession("bills");

        kieSession2.insert(bill);

        kieSession2.fireAllRules();
        kieSession2.dispose();

        List<BillDiscount> billDiscounts = bill.getBillDiscounts();

        // bill discount
        double bill_discount = 0.0;
        for(BillDiscount billDiscount: billDiscounts){
            bill_discount += billDiscount.getDiscount();
        }

        System.out.println("Bill Discount " + bill_discount);

        final_price -= final_price * bill_discount / 100;

        bill.setDiscount(bill_discount);
        bill.setFinalPrice(final_price);

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
