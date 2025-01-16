/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence.service;

import control.exceptions.DAOException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.time.LocalDate;
import java.util.Collection;
import persistence.dao.OrderDAO;
import persistence.model.Cart;
import persistence.model.Order;
import persistence.model.PurchasedMovie;
import persistence.model.RentedMovie;
import persistence.model.Customer;

/**
 *
 * @author roberto
 */
@Stateless
public class OrderService {
    
    @Inject
    private OrderDAO orderDAO;
    @Inject
    private UserService userService;
    @Inject
    private MovieService movieService;
    
    public Collection<Order> retrieveByUser(Customer user) throws DAOException {
        return orderDAO.retrieveByUser(user.getId());
    }
    
    public Order retrieveOrderDetails(Integer orderID, Integer userID) throws DAOException {
        return orderDAO.retrieveOrderDetails(orderID, userID);
    }
    
    public Order retrieveOrderDetails(Integer orderID) throws DAOException {
        return orderDAO.retrieveOrderDetails(orderID);
    }
    
    public Order placeOrder(Customer user, Cart cart) throws DAOException {
        Order order = new Order(user, 
                                cart.getRentedMovies(), 
                                cart.getPurchasedMovies(), 
                                cart.getTotal());
        
        for(PurchasedMovie pm : order.getPurchasedMovies()) {
            pm.setOrder(order);
            movieService.purchaseMovie(pm);
        }
        for(RentedMovie rm : order.getRentedMovies()) {
            rm.setOrder(order);
            movieService.rentMovie(rm);
        }
        
        userService.deductCredit(user, order.getTotal());
        
        orderDAO.save(order);
        
        cart.empty();
        
        return order;
    }
    
    public Collection<Order> retrieveAll() throws DAOException {
        return orderDAO.retrieveAll();
    }
    
    public Collection<Order> retrieveAllBetween(LocalDate from, LocalDate to, Integer userID) throws DAOException {
        return userID == null ? orderDAO.retrieveAllBetween(from, to)
                              : orderDAO.retrieveAllBetween(from, to, userID);
    }
    
}
