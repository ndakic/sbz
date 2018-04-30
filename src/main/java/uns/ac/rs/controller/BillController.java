package uns.ac.rs.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    @GetMapping(value = "/all", produces = "application/json")
    public List<Bill> getAll(){
        return billService.getAllBills();
    }

    @GetMapping(value = "/history/{username}", produces = "application/json")
    public List<Bill> getHistory(@PathVariable String username) throws Exception{
        return billService.userHistory(username);
    }

    @PostMapping(value = "/check_bill")
    public ResponseEntity<Bill> check_bill(@RequestBody Bill b) throws Exception{

        Bill bill = billService.accept_bill(b);

        if(bill == null){ return new ResponseEntity<Bill>(bill, HttpStatus.NO_CONTENT);}

        return new ResponseEntity<Bill>(bill, HttpStatus.OK);
    }

    @PostMapping(value = "/reject_bill")
    public ResponseEntity<Bill> reject_bill(@RequestBody Bill b) throws Exception {

        Bill bill = billService.reject_bill(b);

        return new ResponseEntity<Bill>(bill, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public Bill getAll(@PathVariable Long id) throws Exception{
        return billService.getBillById(id);
    }




    }
