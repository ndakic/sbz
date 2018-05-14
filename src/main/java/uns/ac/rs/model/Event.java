package uns.ac.rs.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Nikola Dakic on 8/8/17.
 */
@Entity
@Table(name = "event")
public class Event implements Serializable {

    private static final long serialVersionUID = -5917738115615452339L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date starts;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy")
    private Date ends;

    private Double discount;

    @ManyToMany(cascade = CascadeType.MERGE)
    private List<ArticleCategory> categories = new ArrayList<ArticleCategory>();

    public Event() {
    }

    public Event(String title, Date starts, Date ends, Double discount, List<ArticleCategory> categories) {
        this.title = title;
        this.starts = starts;
        this.ends = ends;
        this.discount = discount;
        this.categories = categories;
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

    public Date getStarts() {
        return starts;
    }

    public void setStarts(Date starts) {
        this.starts = starts;
    }

    public Date getEnds() {
        return ends;
    }

    public void setEnds(Date ends) {
        this.ends = ends;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public List<ArticleCategory> getCategories() {
        return categories;
    }

    public void setCategories(List<ArticleCategory> categories) {
        this.categories = categories;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", starts=" + starts +
                ", ends=" + ends +
                ", discount=" + discount +
                ", categories=" + categories +
                '}';
    }
}
