package uns.ac.rs.service;

import io.jsonwebtoken.Claims;
import org.kie.api.runtime.KieContainer;
import org.kie.api.runtime.KieSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uns.ac.rs.model.Article;
import uns.ac.rs.model.Bill;
import uns.ac.rs.model.Item;
import uns.ac.rs.model.User;
import uns.ac.rs.model.enums.BillStatus;
import uns.ac.rs.repository.ArticleRepository;
import uns.ac.rs.repository.BillRepository;
import uns.ac.rs.repository.UserRepository;
import uns.ac.rs.security.TokenUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.List;

/**
 * Created by Nikola Dakic on 9/6/17.
 */
@Service
public class BillService {

    @Autowired
    private BillRepository billRepository; //izbrisati

    @Autowired
    private ArticleRepository articleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private KieContainer kieContainer;

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private TokenUtils tokenUtils;

    public List<Bill> getAllBills(){
        return billRepository.findAll();
    }

    public List<Bill> userHistory(String username) throws Exception{

        return billRepository.findAllByBuyerUsername(username);
    }

    public Bill accept_bill(Bill bill) throws Exception {

        bill.setDate(new Date());
        bill.setStatus(BillStatus.INPROCESS);

        // check article supplies
        for (Item item : bill.getItems()) {
            if (item.getArticle().getAmount() < item.getQuantity()) {
                System.out.println("Not Enough Articles!");
                return null;
            }
        }

        // update supplies
        for (Item item : bill.getItems()) {
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

        return bill;

    }

    public Bill reject_bill(Bill bill) throws Exception {

        bill.setStatus(BillStatus.CANCELED);
        billRepository.save(bill);

        return bill;
    }

    public Bill saveBIll(Bill bill) throws Exception{
        return billRepository.save(bill);
    }

    public Bill getBillById(Long id) throws Exception{

        String authToken = request.getHeader("authorization");
        String username = tokenUtils.getUsernameFromToken(authToken);

        Bill bill = billRepository.findOne(id);

        Claims claims = tokenUtils.getClaimsFromToken(authToken);

        String role = claims.get("role").toString();

        // proverava se pripadnost racuna samo ako je korisnik = customer
        if(role.equalsIgnoreCase("customer")){
            if(!(bill.getBuyer().getUsername().equalsIgnoreCase(username)))
                throw new Exception("Not Allowed!");
        }

        return billRepository.findOne(id);

    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        long factor = (long) Math.pow(10, places);
        value = value * factor;
        long tmp = Math.round(value);
        return (double) tmp / factor;
    }

}
