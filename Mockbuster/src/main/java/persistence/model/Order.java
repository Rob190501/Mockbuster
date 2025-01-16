package persistence.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import org.eclipse.persistence.annotations.Cache;
import org.eclipse.persistence.annotations.CacheType;

@Entity
@Table(name = "orders")
@NamedQueries({
    @NamedQuery(name = Order.RETRIEVE_ALL, query = "SELECT o FROM Order o"),
    @NamedQuery(name = Order.RETRIEVE_BY_USERID, query = "SELECT o FROM Order o WHERE o.user.id = :userID"),
    @NamedQuery(name = Order.RETRIEVE_BY_ID_AND_USERID, query = "SELECT o FROM Order o WHERE o.id = :id AND o.user.id = :userID"),
    @NamedQuery(name = Order.RETRIEVE_ALL_BETWEEN_DATES, query = "SELECT o FROM Order o WHERE o.date BETWEEN :from AND :to"),
    @NamedQuery(name = Order.RETRIEVE_ALL_BETWEEN_DATES_BY_USERID, query = "SELECT o FROM Order o WHERE o.date BETWEEN :from AND :to AND o.user.id = :userID")
})
@Cache(
    type = CacheType.SOFT, // Gli oggetti rimangono nella cache finché la memoria è sufficiente
    size = 100,            // Numero massimo di oggetti nella cache
    expiry = 3600000       // Tempo di scadenza in millisecondi (1 ora)
)
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static final String RETRIEVE_ALL = "Order.RETRIEVE_ALL";
    public static final String RETRIEVE_BY_USERID = "Order.RETRIEVE_BY_USERID";
    public static final String RETRIEVE_BY_ID_AND_USERID = "RETRIEVE_BY_ID_AND_USERID";
    public static final String RETRIEVE_ALL_BETWEEN_DATES = "Order.RETRIEVE_ALL_BETWEEN_DATES";
    public static final String RETRIEVE_ALL_BETWEEN_DATES_BY_USERID = "Order.RETRIEVE_ALL_BETWEEN_DATES_BY_USERID";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "order_date", nullable = false)
    private LocalDate date;

    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private Float total;

    @ManyToOne(optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private Customer user;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<RentedMovie> rentedMovies = new ArrayList<>();;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private Collection<PurchasedMovie> purchasedMovies = new ArrayList<>();;

    public Order() {
    }

    public Order(Customer user) {
        this.id = -1;
        this.date = LocalDate.now();
        this.total = 0.0f;
        this.user = user;
    }

    public Order(Integer id, LocalDate date, Float total) {
        this.id = id;
        this.date = date;
        this.total = total;
        this.user = null;
    }

    public Order(Integer id, LocalDate date, Float total, Customer user) {
        this.id = id;
        this.date = date;
        this.total = total;
        this.user = user;
    }
    
    public Order(Customer user, Collection<RentedMovie> rentedMovies, Collection<PurchasedMovie> purchasedMovies, Float total) {
        this.id = -1;
        this.date = LocalDate.now();
        this.total = total;
        this.user = user;
        this.rentedMovies = rentedMovies;
        this.purchasedMovies = purchasedMovies;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public Float getTotal() {
        return total;
    }

    public void setTotal(Float total) {
        this.total = total;
    }

    public Customer getUser() {
        return user;
    }

    public void setUser(Customer user) {
        this.user = user;
    }

    public Collection<RentedMovie> getRentedMovies() {
        return rentedMovies;
    }

    public void setRentedMovies(Collection<RentedMovie> rentedMovies) {
        this.rentedMovies = rentedMovies;
    }

    public void addRentedMovie(RentedMovie movie) {
        this.rentedMovies.add(movie);
    }

    public Collection<PurchasedMovie> getPurchasedMovies() {
        return purchasedMovies;
    }

    public void setPurchasedMovies(Collection<PurchasedMovie> purchasedMovies) {
        this.purchasedMovies = purchasedMovies;
    }

    public void addPurchasedMovie(PurchasedMovie movie) {
        this.purchasedMovies.add(movie);
    }
}
