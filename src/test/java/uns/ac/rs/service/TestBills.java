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
import uns.ac.rs.repository.ArticleRepository;
import uns.ac.rs.repository.BillRepository;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Nikola Dakic on 9/6/17.
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
public class TestBills {

    @Autowired
    BillRepository billRepository;

    @Autowired
    BillService billService;

    @Autowired
    UserService userService;

    @Autowired
    ArticleRepository articleRepository;

    @Test
    public void testUserBillsHistory() throws Exception{

        String validUsername = "daka1";
        String invalidUsername = "blabla";

        List<Bill> userBillsValid = billRepository.findAllByBuyerUsername(validUsername);
        List<Bill> userBillsInvalid = billRepository.findAllByBuyerUsername(invalidUsername);

        assertThat(userBillsValid).isNotNull();
        assertThat(userBillsInvalid).isNullOrEmpty();

    }

    @Test
    @Transactional
    @Rollback(true)
    public void testAcceptBill() throws Exception{

        Article art = articleRepository.findOne(3L);
        User user = userService.findUser("daka1");

        //System.out.println("user: " + user.toString());

        Item item = new Item();
        item.setArticle(art);
        item.setPrice(art.getPrice());
        //item.setQuantity(50);
        item.setQuantity(5);

        List<Item> items = new ArrayList<>();
        items.add(item);

        Bill bill = new Bill();
        bill.setItems(items);
        bill.setBuyer(user);
        bill.setReceivedPoints(200.0); //fixed

        Bill billCheck = billService.accept_bill(bill);

        //assertThat(billCheck).isNull();
        assertThat(billCheck).isNotNull();

    }




}
