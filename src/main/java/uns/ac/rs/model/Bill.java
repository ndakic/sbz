package uns.ac.rs.model;

import uns.ac.rs.model.enums.BillStatus;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Nikola Dakic on 7/23/17.
 */

@Entity
@Table(name = "bill")
public class Bill implements Serializable{

    private static final long serialVersionUID = 801396560393430972L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date = null;

    @ManyToOne
    private User buyer;

    @Enumerated(EnumType.STRING)
    private BillStatus status;

    private Double currentPrice;

    private Double discount;

    private Double finalPrice;

    private Double receivedPoints;

    private Double spentPoints;

    @OneToMany
    private List<BillDiscount> billDiscounts = new ArrayList<BillDiscount>();

    @OneToMany
    private List<Item> items = new ArrayList<Item>();


    // getters and setters

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }

    public BillStatus getStatus() {
        return status;
    }

    public void setStatus(BillStatus status) {
        this.status = status;
    }

    public Double getCurrentPrice() {
        return currentPrice;
    }

    public void setCurrentPrice(Double currentPrice) {
        this.currentPrice = currentPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(Double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public Double getReceivedPoints() {
        return receivedPoints;
    }

    public void setReceivedPoints(Double receivedPoints) {
        this.receivedPoints = receivedPoints;
    }

    public Double getSpentPoints() {
        return spentPoints;
    }

    public void setSpentPoints(Double spentPoints) {
        this.spentPoints = spentPoints;
    }

    public List<BillDiscount> getBillDiscounts() {
        return billDiscounts;
    }

    public void setBillDiscounts(List<BillDiscount> billDiscounts) {
        this.billDiscounts = billDiscounts;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }
}
