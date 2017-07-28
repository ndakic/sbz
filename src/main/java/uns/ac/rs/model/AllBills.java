package uns.ac.rs.model;

import java.util.List;

/**
 * Created by Nikola Dakic on 7/28/17.
 */
public class AllBills {

    private List<Bill> bills;

    public AllBills(List<Bill> bills) {
        this.bills = bills;
    }

    public List<Bill> getBills() {
        return bills;
    }

    public void setBills(List<Bill> bills) {
        this.bills = bills;
    }
}
