/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
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

/**
 *
 * @author roberto
 */
@Entity
@Table(name = "catalog_manager")
@NamedQueries({
    @NamedQuery(name = CatalogManager.RETRIEVE_BY_EMAIL_AND_PSW, query = "SELECT cm FROM CatalogManager cm WHERE cm.email = :email AND cm.password = :password")
})
public class CatalogManager implements Serializable {

    private static final long serialVersionUID = 1L;
    
    public static final String RETRIEVE_BY_EMAIL_AND_PSW = "CatalogManager.RETRIEVE_BY_EMAIL_AND_PSW";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "email", nullable = false, length = 200, unique = true)
    private String email;

    @Column(name = "password", nullable = false, length = 200)
    private String password;

    public CatalogManager() {
    }

    public CatalogManager(Integer id, String email, String password) {
        this.id = id;
        this.email = email;
        this.password = password;
    }

    public CatalogManager(String email, String password) {
        this.email = email;
        this.password = password;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
