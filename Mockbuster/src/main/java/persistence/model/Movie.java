package persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "movie")
@NamedQueries({
    @NamedQuery(name = Movie.RETRIEVE_ALL, query = "SELECT m FROM Movie m"),
    @NamedQuery(name = Movie.RETRIEVE_BY_TITLE, query = "SELECT m FROM Movie m WHERE m.title LIKE :title")
})
public class Movie implements Serializable {
	
    private static final long serialVersionUID = 1L;
    
    public static final String RETRIEVE_ALL = "Movie.RETRIEVE_ALL";
    public static final String RETRIEVE_BY_TITLE = "Movie.RETRIEVE_BY_TITLE";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "title", nullable = false, length = 200)
    private String title;

    @Column(name = "plot", nullable = false, length = 200)
    private String plot;

    @Column(name = "duration", nullable = false)
    private Integer duration;

    @Column(name = "movie_year", nullable = false)
    private Integer year;

    @Column(name = "available_licenses", nullable = false)
    private Integer availableLicenses;

    @Column(name = "daily_rental_price", nullable = false, precision = 10, scale = 2)
    private Float dailyRentalPrice;

    @Column(name = "purchase_price", nullable = false, precision = 10, scale = 2)
    private Float purchasePrice;

    @Column(name = "is_visible", nullable = false)
    private Boolean isVisible;

    @Column(name = "poster_path", nullable = false, length = 200)
    private String posterPath;

    
    
    public Movie() {
    }

    public Movie(Integer id, String title, String plot, Integer duration, Integer year, Integer availableLicenses,
            Float dailyRentalPrice, Float purchasePrice, Boolean isVisible, String posterPath) {
        this.id = id;
        this.title = title;
        this.plot = plot;
        this.duration = duration;
        this.year = year;
        this.availableLicenses = availableLicenses;
        this.dailyRentalPrice = dailyRentalPrice;
        this.purchasePrice = purchasePrice;
        this.isVisible = isVisible;
        this.posterPath = posterPath;
    }

    public Movie(String title, String plot, Integer duration, Integer year, Integer availableLicenses,
            Float dailyRentalPrice, Float purchasePrice, String posterPath) {
        this.id = -1;
        this.title = title;
        this.plot = plot;
        this.duration = duration;
        this.year = year;
        this.availableLicenses = availableLicenses;
        this.dailyRentalPrice = dailyRentalPrice;
        this.purchasePrice = purchasePrice;
        this.isVisible = Boolean.TRUE;
        this.posterPath = posterPath;
    }

    public Movie(Integer id, String title, String plot, Integer duration, Integer year, Integer availableLicenses,
            Float dailyRentalPrice, Float purchasePrice, Boolean isVisible) {
        this.id = id;
        this.title = title;
        this.plot = plot;
        this.duration = duration;
        this.year = year;
        this.availableLicenses = availableLicenses;
        this.dailyRentalPrice = dailyRentalPrice;
        this.purchasePrice = purchasePrice;
        this.isVisible = isVisible;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPlot() {
        return plot;
    }

    public void setPlot(String plot) {
        this.plot = plot;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Integer getYear() {
        return year;
    }

    public void setYear(Integer year) {
        this.year = year;
    }

    public Integer getAvailableLicenses() {
        return availableLicenses;
    }

    public void setAvailableLicenses(Integer availableLicenses) {
        this.availableLicenses = availableLicenses;
    }
    
    public void deductAvailableLicences(Integer amount) {
        this.availableLicenses -= amount;
    }

    public Float getDailyRentalPrice() {
        return dailyRentalPrice;
    }

    public void setDailyRentalPrice(Float dailyRentalPrice) {
        this.dailyRentalPrice = dailyRentalPrice;
    }

    public Float getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(Float purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Boolean isVisible() {
        return isVisible;
    }

    public void setVisible(Boolean isVisible) {
        this.isVisible = isVisible;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}
