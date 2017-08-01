package uns.ac.rs.controller;

import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uns.ac.rs.model.*;
import uns.ac.rs.model.enums.BillStatus;
import uns.ac.rs.model.enums.DiscountType;
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
        article.setOrderStatus(false);
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

        bill.setDate(new Date());
        bill.setFactVariable(0);

        KieSession kieSession = kieContainer.newKieSession("articles");

        // insert All Bills
        List<Bill> bills = billRepository.findAll();
        AllBills allBills = new AllBills(bills);
        kieSession.insert(allBills);

        // init BillDiscounts and Set User
        bill.setBillDiscounts(new ArrayList<BillDiscount>());
        User buyer = userRepository.findOneByUsername(bill.getBuyer().getUsername());
        buyer.setPassword("sensitive-data");
        bill.setBuyer(buyer);


        List<Item> items = bill.getItems();

        //  items updates
        int ord = 1;
        double currentPrice = 0;

        for(Item item: items){
            item.setBill(bill);
            double itemPrice = item.getPrice() * item.getQuantity();
            currentPrice += itemPrice;
            item.setCurrentPrice(itemPrice);
            item.setItemDiscounts(new ArrayList<ItemDiscount>());
            item.setOrder(ord);
            ord++;

            kieSession.insert(item);
        }
        bill.setCurrentPrice(currentPrice);

        // activate rules
        kieSession.insert(bill);
        kieSession.fireAllRules();
        kieSession.dispose();

        // calculate items discount
        double bill_final_price = 0.0;
        for (Item item: items) {

            List<ItemDiscount> discounts = item.getItemDiscounts();

            System.out.println("size: " + discounts.size());

            // if there is no discounts
            if(discounts.size() == 0){
                bill_final_price += item.getCurrentPrice();
                item.setFinalPrice(item.getCurrentPrice());
                item.set_discount(0.0);
            }else{
                // count discounts
                double basicDiscount = 0;
                double additionDiscount = 0;

                for(ItemDiscount itemDiscount: discounts){
                    if(itemDiscount.getType().equals(DiscountType.BASIC) && itemDiscount.getDiscount() > basicDiscount){
                        basicDiscount = itemDiscount.getDiscount();
                        System.out.println("basic: " + basicDiscount);
                    }
                    if(itemDiscount.getType().equals(DiscountType.ADVANCED)){
                        additionDiscount += itemDiscount.getDiscount();
                    }
                }

                double finalItemDiscount = basicDiscount + additionDiscount;

                System.out.println("finalDiscount: " + finalItemDiscount);

                if(finalItemDiscount > item.getArticle().getArticleCategory().getDiscount()){
                    finalItemDiscount = item.getArticle().getArticleCategory().getDiscount();
                }

                item.set_discount(finalItemDiscount);

                item.setFinalPrice(item.getCurrentPrice() - (item.getCurrentPrice() * finalItemDiscount / 100));

                bill_final_price += item.getFinalPrice();

                System.out.println("dis " + item.getDiscount());
                System.out.println("final Price: " + item.getFinalPrice());
            }


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

        bill_final_price -= bill_final_price * bill_discount / 100;

        bill.setDiscount(bill_discount);
        bill.setFinalPrice(bill_final_price);

        return new ResponseEntity<Bill>(bill, HttpStatus.OK);
    }


    @PostMapping(value = "/submit_bill")
    public ResponseEntity<Bill> submit_bill(@RequestBody Bill bill) throws Exception{

        bill.setDate(new Date());
        bill.setStatus(BillStatus.INPROCESS);

        billRepository.save(bill);

        return new ResponseEntity<Bill>(bill, HttpStatus.OK);
    }

    @GetMapping(value = "/orders")
    public List<Article> orders() throws Exception{

        List<Article> articles = articleRepository.findAll();
        List<Bill> bills = billRepository.findAll();

        KieSession kieSession = kieContainer.newKieSession("order");

        for(Article article: articles){
            kieSession.insert(article);
        }

        for(Bill bill: bills){
            kieSession.insert(bill);
        }

        kieSession.fireAllRules();
        kieSession.dispose();

        return articles;
    }

    @PostMapping(value = "/order_more")
    public ResponseEntity<Article> order_more(@RequestBody Article article){

        Integer quantity = article.getOrderQuantity();

        if(quantity == null){
            quantity = 0;
        }

        article.setOrderStatus(false);
        article.setOrderMessage(null);
        article.setOrderQuantity(null);


        article.setAmount(article.getAmount() + (quantity + article.getMin() - article.getAmount()));

        Article art = articleRepository.save(article);
        return Optional.ofNullable(art)
                .map(result -> new ResponseEntity<>(art, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));

    }

}
