package uns.ac.rs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uns.ac.rs.model.Bill;
import uns.ac.rs.repository.BillRepository;

import java.util.List;

/**
 * Created by Nikola Dakic on 7/26/17.
 */

@RestController
@RequestMapping("/api/bill")
public class BillController {

    @Autowired
    private BillRepository billRepository;

    @GetMapping(value = "/all")
    public List<Bill> getAll(){
        return billRepository.findAll();
    }

}
