/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence.service;

import control.exceptions.DAOException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Collection;
import persistence.dao.UserDAO;
import persistence.model.CatalogManager;
import persistence.model.Customer;
import persistence.model.OrderManager;

/**
 *
 * @author roberto
 */
@Stateless
public class UserService {
    @Inject
    private UserDAO userDAO;
    
    public void signup(Customer user) throws DAOException, NoSuchAlgorithmException {
        String hashedPassword = toHash(user.getPassword());
        user.setPassword(hashedPassword);
        userDAO.save(user);
    }
    
    public Customer customerLogin(String email, String password) throws DAOException, NoSuchAlgorithmException {
        password = toHash(password);
        return userDAO.retrieveByEmailAndPassword(email, password, Customer.class);
    }
    
    public CatalogManager catalogManagerLogin(String email, String password) throws DAOException, NoSuchAlgorithmException {
        password = toHash(password);
        return userDAO.retrieveByEmailAndPassword(email, password, CatalogManager.class);
    }
    
    public OrderManager orderManagerLogin(String email, String password) throws DAOException, NoSuchAlgorithmException {
        password = toHash(password);
        return userDAO.retrieveByEmailAndPassword(email, password, OrderManager.class);
    }
    
    private String toHash(String password) throws NoSuchAlgorithmException {
        String hashString = null;
        java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-512");
        byte[] hash = digest.digest(password.getBytes(StandardCharsets.UTF_8));
        hashString = "";
        for (byte element : hash) {
            hashString += Integer.toHexString((element & 0xFF) | 0x100).substring(1, 3);
        }
        return hashString;
    }
    
    public Collection<Customer> retrieveAll() throws DAOException {
        return userDAO.retrieveAll();
    }
    
    public void deductCredit(Customer user, Float amount) throws DAOException {
        user.deductCredit(amount);
        userDAO.update(user);
    }
    
    public void setAdmin(Integer id, Boolean isAdmin) throws DAOException {
        //userDAO.setAdmin(id, isAdmin);
    }
    
    public void update(Customer user) throws DAOException {
        userDAO.update(user);
    }
    
    public Boolean checkEmailAvailability(String email) throws DAOException {
        return userDAO.checkEmailAvailability(email);
    }
    
    
}
