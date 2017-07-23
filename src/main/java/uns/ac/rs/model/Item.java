package uns.ac.rs.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.Min;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Nikola Dakic on 7/23/17.
 */

@Entity
@Table(name = "item")
public class Item implements Serializable{

    private static final long serialVersionUID = -6445461894676726593L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @JsonIgnore
    @ManyToOne
    private Bill bill;

    private Integer itemOrder;

    @OneToOne
    private Article article;

    private Double price;

    @Min(value = 1)
    private Integer quantity;

    private Double currentPrice;

    private Double discount;

    private Double finalPrice;

    @OneToMany
    private List<ItemDiscount> itemDiscounts = new ArrayList<ItemDiscount>();


    private void addPopust(ItemDiscount d){
        itemDiscounts.add(d);
    }


    //getters and setters

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Bill getBill() {
        return bill;
    }

    public void setBill(Bill bill) {
        this.bill = bill;
    }

    public Integer getOrder() {
        return itemOrder;
    }

    public void setOrder(Integer order) {
        this.itemOrder = order;
    }

    public Article getArticle() {
        return article;
    }

    public void setArticle(Article article) {
        this.article = article;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
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

    public List<ItemDiscount> getItemDiscounts() {
        return itemDiscounts;
    }

    public void setItemDiscounts(List<ItemDiscount> itemDiscounts) {
        this.itemDiscounts = itemDiscounts;
    }
}
