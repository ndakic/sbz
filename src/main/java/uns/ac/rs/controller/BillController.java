package uns.ac.rs.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import uns.ac.rs.model.Bill;
import uns.ac.rs.service.BillService;

import java.util.List;

/**
 * Created by Nikola Dakic on 7/26/17.
 */

@RestController
@RequestMapping("/api/bill")
public class BillController {

    @Autowired
    BillService billService;

    private static final Logger logger = LogManager.getLogger(BillController.class);


    @GetMapping(value = "/all", produces = "application/json")
    public List<Bill> getAll(){

        List<Bill> bills = billService.getAllBills();

        for(Bill bill: bills)
            bill.getBuyer().setPassword("PROTECTED!");

        return bills;
    }

    @GetMapping(value = "/history/{username}", produces = "application/json")
    public List<Bill> getHistory(@PathVariable String username) throws Exception{

        List<Bill> bills = billService.userHistory(username);

        for(Bill bill: bills)
            bill.getBuyer().setPassword("PROTECTED!");

        return bills;
    }


    @PostMapping(value = "/check_bill")
    public ResponseEntity<Bill> check_bill(@RequestBody Bill b) throws Exception{

        Bill bill = billService.accept_bill(b);

        if(bill == null){ return new ResponseEntity<Bill>(bill, HttpStatus.NO_CONTENT);}

        bill.getBuyer().setPassword("PROTECTED!");
        logger.info(bill);

        return new ResponseEntity<Bill>(bill, HttpStatus.OK);
    }

    @PostMapping(value = "/reject_bill")
    public ResponseEntity<Bill> reject_bill(@RequestBody Bill b) throws Exception {

        Bill bill = billService.reject_bill(b);
        bill.getBuyer().setPassword("PROTECTED!");

        return new ResponseEntity<Bill>(bill, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Bill getBill(@PathVariable Long id) throws Exception{

        Bill bill = billService.getBillById(id);
        bill.getBuyer().setPassword("PROTECTED!");

        return billService.getBillById(id);
    }




    }
