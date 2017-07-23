package uns.ac.rs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import uns.ac.rs.model.enums.DiscountType;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Nikola Dakic on 7/23/17.
 */

@Entity
@Table(name = "item_discount")
public class ItemDiscount implements Serializable {

    private static final long serialVersionUID = 6981878675735209251L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Bill account;

    @JsonIgnore
    @ManyToOne
    private Item item;

    private Double discount;

    @Enumerated(EnumType.STRING)
    private DiscountType type;


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

    public Item getItem() {
        return item;
    }

    public void setItem(Item item) {
        this.item = item;
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
}
