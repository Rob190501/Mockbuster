package model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "movie_purchase_order")
public class PurchasedMovie implements Serializable {

    private static final long serialVersionUID = 1L;
    
    //public static final String RETRIEVE_BY_ORDERID_AND_USERID = "PurchasedMovie.RETRIEVE_BY_ORDERID_AND_USERID";
   
    @EmbeddedId
    private OrderedMovieKey id;

    @MapsId("orderID")
    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @MapsId("movieID")
    @ManyToOne
    @JoinColumn(name = "movie_id")
    private Movie movie;

    @Column(name = "price", nullable = false, precision = 10, scale = 2)
    private Float price;

    public PurchasedMovie() {
    }

    public PurchasedMovie(Movie movie, Order order, Float price) {
        this.movie = movie;
        this.order = order;
        this.price = price;
    }

    public PurchasedMovie(Movie movie, Float price) {
        this.movie = movie;
        this.order = null;
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Movie getMovie() {
        return movie;
    }

    public void setMovie(Movie movie) {
        this.movie = movie;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }
}
