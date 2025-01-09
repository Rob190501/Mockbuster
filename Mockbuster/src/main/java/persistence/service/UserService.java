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
import persistence.model.User;

/**
 *
 * @author roberto
 */
@Stateless
public class UserService {
    @Inject
    private UserDAO userDAO;
    
    public void signup(User user) throws DAOException, NoSuchAlgorithmException {
        String hashedPassword = toHash(user.getPassword());
        user.setPassword(hashedPassword);
        userDAO.save(user);
    }
    
    public User login(String email, String password) throws DAOException, NoSuchAlgorithmException {
        password = toHash(password);
        return userDAO.retrieveByEmailAndPassword(email, password);
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
    
    public Collection<User> retrieveAll() throws DAOException {
        return userDAO.retrieveAll();
    }
    
    public void deductCredit(User user, Float amount) throws DAOException {
        user.deductCredit(amount);
        userDAO.update(user);
    }
    
    public void setAdmin(Integer id, Boolean isAdmin) throws DAOException {
        //userDAO.setAdmin(id, isAdmin);
    }
    
    public void update(User user) throws DAOException {
        userDAO.update(user);
    }
    
    public Boolean checkEmailAvailability(String email) throws DAOException {
        return userDAO.checkEmailAvailability(email);
    }
    
    
}
