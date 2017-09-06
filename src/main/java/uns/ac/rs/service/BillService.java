package uns.ac.rs.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import uns.ac.rs.model.Bill;
import uns.ac.rs.repository.BillRepository;

import java.util.List;

/**
 * Created by Nikola Dakic on 9/6/17.
 */
@Service
public class BillService {

    @Autowired
    BillRepository billRepository;

    public List<Bill> userHistory(String username) throws Exception{

        return billRepository.findAllByBuyerUsername(username);
    }

}
