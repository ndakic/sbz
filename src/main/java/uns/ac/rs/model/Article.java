package uns.ac.rs.model;

import org.hibernate.validator.constraints.SafeHtml;
import uns.ac.rs.model.enums.StatusOfArticle;

import javax.persistence.*;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by Nikola Dakic on 7/19/17.
 */

@Entity
@Table(name = "article")
public class Article implements Serializable {

    private static final long serialVersionUID = 3847911005360730620L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @SafeHtml
    private String title;

    @ManyToOne(cascade = {CascadeType.ALL})
    private ArticleCategory articleCategory;

    @NotNull
    @Min(value = 0)
    private Double price;

    @Min(value = 0)
    private Integer amount;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;

    @Enumerated(EnumType.STRING)
    private StatusOfArticle status;

    @Min(value = 0)
    private Integer min;

    private Boolean orderStatus = false;

    private String orderMessage;

    private Integer orderQuantity;

    public Article() {
    }

    public Article(String title, ArticleCategory articleCategory, Double price, Integer amount, Date date, StatusOfArticle status, Integer min, Boolean orderStatus, String orderMessage, Integer orderQuantity) {
        this.title = title;
        this.articleCategory = articleCategory;
        this.price = price;
        this.amount = amount;
        this.date = date;
        this.status = status;
        this.min = min;
        this.orderStatus = orderStatus;
        this.orderMessage = orderMessage;
        this.orderQuantity = orderQuantity;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public ArticleCategory getArticleCategory() {
        return articleCategory;
    }

    public void setArticleCategory(ArticleCategory articleCategory) {
        this.articleCategory = articleCategory;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public StatusOfArticle getStatus() {
        return status;
    }

    public void setStatus(StatusOfArticle status) {
        this.status = status;
    }

    public Integer getMin() {
        return min;
    }

    public void setMin(Integer min) {
        this.min = min;
    }

    public Boolean getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Boolean orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderMessage() {
        return orderMessage;
    }

    public void setOrderMessage(String orderMessage) {
        this.orderMessage = orderMessage;
    }

    public Integer getOrderQuantity() {
        return orderQuantity;
    }

    public void setOrderQuantity(Integer orderQuantity) {
        this.orderQuantity = orderQuantity;
    }

    @Override
    public String toString() {
        return "Article{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", articleCategory=" + articleCategory +
                ", price=" + price +
                ", amount=" + amount +
                ", date=" + date +
                ", status=" + status +
                ", min=" + min +
                ", orderStatus=" + orderStatus +
                ", orderMessage='" + orderMessage + '\'' +
                ", orderQuantity=" + orderQuantity +
                '}';
    }
}
