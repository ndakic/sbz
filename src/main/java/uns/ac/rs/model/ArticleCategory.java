package uns.ac.rs.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by Nikola Dakic on 7/19/17.
 */


@Entity
@Table(name = "article_category")
public class ArticleCategory implements Serializable {


    private static final long serialVersionUID = 4312635525209859882L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @ManyToOne(cascade = {CascadeType.REFRESH})
    private ArticleCategory articleCategory;

    private Double discount;

    public ArticleCategory() {
    }

    public ArticleCategory(String title, Double discount, ArticleCategory articleCategory) {
        this.title = title;
        this.articleCategory = articleCategory;
        this.discount = discount;
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

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    @Override
    public String toString() {
        return "ArticleCategory{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", articleCategory=" + articleCategory +
                ", discount=" + discount +
                '}';
    }
}
