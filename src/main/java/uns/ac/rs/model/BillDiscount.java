package uns.ac.rs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uns.ac.rs.model.enums.DiscountType;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Nikola Dakic on 7/23/17.
 */

@Entity
@Table(name = "bill_discount")
public class BillDiscount implements Serializable{

    private static final long serialVersionUID = -561718873127485145L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Bill account;

    private Double discount;

    @Enumerated(EnumType.STRING)
    private DiscountType type;

    public BillDiscount() {
    }

    public BillDiscount(Bill account, Double discount, DiscountType type) {
        this.account = account;
        this.discount = discount;
        this.type = type;
    }

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

    public Bill getAccount() {
        return account;
    }

    public void setAccount(Bill account) {
        this.account = account;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public DiscountType getType() {
        return type;
    }

    public void setType(DiscountType type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "BillDiscount{" +
                "id=" + id +
                ", account=" + account +
                ", discount=" + discount +
                ", type=" + type +
                '}';
    }
}
