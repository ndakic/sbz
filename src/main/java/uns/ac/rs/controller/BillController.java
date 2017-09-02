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
import uns.ac.rs.model.User;
import uns.ac.rs.model.enums.BillStatus;
import uns.ac.rs.repository.ArticleRepository;
import uns.ac.rs.repository.BillRepository;
import uns.ac.rs.repository.UserRepository;

import java.util.Date;
import java.util.List;

/**
 * Created by Nikola Dakic on 7/26/17.
 */

@RestController
@RequestMapping("/api/bill")
public class BillController {

    @Autowired
    private BillRepository billRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KieContainer kieContainer;

    @GetMapping(value = "/all", produces = "application/json")
    public List<Bill> getAll(){
        return billRepository.findAll();
    }

    @GetMapping(value = "/history/{username}", produces = "application/json")
    public List<Bill> getHistory(@PathVariable String username){
        return billRepository.findOneByBuyerUsername(username);
    }

    @PostMapping(value = "/check_bill")
    public ResponseEntity<Bill> check_bill(@RequestBody Bill bill) throws Exception{

        bill.setDate(new Date());
        bill.setStatus(BillStatus.INPROCESS);



        // check article supplies
        for(Item item: bill.getItems()){
            System.out.println("Amount:" +  item.getArticle().getAmount());
            System.out.println("Quantity:" +  item.getQuantity());
            if(item.getArticle().getAmount() < item.getQuantity()){
                System.out.println("Not Enough Articles!");
                return new ResponseEntity<Bill>(bill, HttpStatus.NO_CONTENT);
            }
        }

        // update supplies
        for(Item item: bill.getItems()){
            Article art = item.getArticle();
            art.setAmount(art.getAmount() - item.getQuantity());
            articleRepository.save(art);
        }

        // update bill status
        bill.setStatus(BillStatus.SUCCESSFUL);


        KieSession kieSession = kieContainer.newKieSession("bonus");

        kieSession.insert(bill);

        kieSession.fireAllRules();
        kieSession.dispose();

        double received = round(bill.getReceivedPoints(), 2);
        System.out.println("Gained points: " + received);

        // update user points
        User user = userRepository.findOneByUsername(bill.getBuyer().getUsername());
        user.getUserProfile().setPoints(user.getUserProfile().getPoints() + received);
        userRepository.save(user);


        billRepository.save(bill);

        return new ResponseEntity<Bill>(bill, HttpStatus.OK);
    }

    @PostMapping(value = "/reject_bill")
    public ResponseEntity<Bill> reject_bill(@RequestBody Bill bill) throws Exception {

        bill.setStatus(BillStatus.CANCELED);
        billRepository.save(bill);

        return new ResponseEntity<Bill>(bill, HttpStatus.OK);
    }


    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

    }
