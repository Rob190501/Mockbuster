package persistence.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.NamedQueries;
import jakarta.persistence.NamedQuery;
import java.io.Serializable;

@Entity
@Table(name = "user")
@NamedQueries({
    @NamedQuery(name = Customer.RETRIEVE_ALL, query = "SELECT c FROM Customer c"),
    @NamedQuery(name = Customer.RETRIEVE_BY_EMAIL_AND_PSW, query = "SELECT c FROM Customer c WHERE c.email = :email AND c.password = :password"),
    @NamedQuery(name = Customer.CHECK_EMAIL_AVAILABILITY, query = "SELECT COUNT(c) FROM Customer c WHERE c.email = :email"
    )
})
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static final String RETRIEVE_ALL = "Customer.RETRIEVE_ALL";
    public static final String RETRIEVE_BY_EMAIL_AND_PSW = "Customer.RETRIEVE_BY_EMAIL_AND_PSW";
    public static final String CHECK_EMAIL_AVAILABILITY = "Customer.CHECK_EMAIL_AVAILABILITY";
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "email", nullable = false, length = 200, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 200)
    private String password;

    @Column(name = "first_name", nullable = false, length = 200)
    private String firstName;

    @Column(name = "last_name", nullable = false, length = 200)
    private String lastName;

    @Column(name = "billing_address", nullable = false, length = 200)
    private String billingAddress;

    @Column(name = "credit", nullable = false, precision = 10, scale = 2)
    private Float credit = 0.0f;

    @Column(name = "is_admin", nullable = false)
    private Boolean isAdmin = false;

    public Customer() {
    }

    public Customer(Integer id, String email, String password, String firstName, String lastName, String billingAddress, Float credit, Boolean isAdmin) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.billingAddress = billingAddress;
        this.credit = credit;
        this.isAdmin = isAdmin;
    }

    public Customer(String email, String password, String firstName, String lastName, String billingAddress) {
        this.id = -1;
        this.email = email;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.billingAddress = billingAddress;
        this.credit = 0.0f;
        this.isAdmin = Boolean.FALSE;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String username) {
        this.email = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBillingAddress() {
        return billingAddress;
    }

    public void setBillingAddress(String billingAddress) {
        this.billingAddress = billingAddress;
    }

    public Float getCredit() {
        return credit;
    }

    public void setCredit(Float credit) {
        this.credit = credit;
    }
    
    public void deductCredit(Float amount) {
        this.credit -= amount;
    }

    public Boolean isAdmin() {
        return isAdmin;
    }

    public void setAdmin(Boolean isAdmin) {
        this.isAdmin = isAdmin;
    }
}
