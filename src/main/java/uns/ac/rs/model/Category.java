package uns.ac.rs.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Nikola Dakic on 7/19/17.
 */


@Entity
@Table(name = "article_category")
public class Category implements Serializable {


    private static final long serialVersionUID = 4312635525209859882L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @ManyToOne
    private Category category;

    private Double discount;



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

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }
}
