package uns.ac.rs.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import uns.ac.rs.model.Bill;
import uns.ac.rs.repository.BillRepository;

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

    @Test
    public void testUserBillsHistory(){

        String validUsername = "daka";
        String invalidUsername = "blabla";

        List<Bill> userBillsValid = billRepository.findAllByBuyerUsername(validUsername);
        List<Bill> userBillsInvalid = billRepository.findAllByBuyerUsername(invalidUsername);

        assertThat(userBillsValid).isNotNull();
        assertThat(userBillsInvalid).isNullOrEmpty();

    }




}
