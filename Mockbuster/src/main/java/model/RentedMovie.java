package model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "movie_rental_order")
public class RentedMovie implements Serializable {

    private static final long serialVersionUID = 1L;
    
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

    @Column(name = "daily_price", nullable = false, precision = 10, scale = 2)
    private Float dailyPrice;

    @Column(name = "days", nullable = false)
    private Integer days;

    public RentedMovie() {
    }

    public RentedMovie(Movie movie, Order order, Float dailyPrice, Integer days) {
        this.movie = movie;
        this.order = order;
        this.dailyPrice = dailyPrice;
        this.days = days;
    }

    public RentedMovie(Movie movie, Float dailyPrice, Integer days) {
        this.movie = movie;
        this.order = null;
        this.dailyPrice = dailyPrice;
        this.days = days;
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

    public Float getDailyPrice() {
        return dailyPrice;
    }

    public void setDailyPrice(Float dailyPrice) {
        this.dailyPrice = dailyPrice;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }
}
