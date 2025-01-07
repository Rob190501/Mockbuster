package model.dao;

import java.time.LocalDate;

import java.util.Collection;
import control.exceptions.DAOException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import model.Movie;
import model.Order;
import model.PurchasedMovie;
import model.RentedMovie;
import model.User;

@Stateless
public class OrderDAO {
    
    @Inject
    private EntityManager em;

    public void placeOrder(Order order) throws DAOException {
        try {
            em.persist(order);
            
            User customer = em.find(User.class, order.getUser().getId());
            customer.decreaseCredit(order.getTotal());
            
            for(PurchasedMovie pm : order.getPurchasedMovies()) {
                Movie orderedMovie = em.find(Movie.class, pm.getMovie().getId());
                orderedMovie.decreaseAvailableLicences(1);
            }
            
            for(RentedMovie rm : order.getRentedMovies()) {
                Movie orderedMovie = em.find(Movie.class, rm.getMovie().getId());
                orderedMovie.decreaseAvailableLicences(rm.getDays());
            }
        } catch(Exception e) {
            throw new DAOException(e);
        }
    }

    public Collection<Order> retrieveByUser(int userID) throws DAOException {
        try {
            return new ArrayList<Order>(em.createNamedQuery(Order.RETRIEVE_BY_USERID, Order.class)
                                        .setParameter("userID", userID)
                                        .getResultList());
        } catch(Exception e) {
            throw new DAOException(e);
        }
    }

    public Order retrieveOrderDetails(Integer userID, Integer orderID) throws DAOException {
        try {
            Order out = em.createNamedQuery(Order.RETRIEVE_BY_ID_AND_USERID, Order.class)
                        .setParameter("id", orderID)
                        .setParameter("userID", userID)
                        .getSingleResult();
            out.getPurchasedMovies().size();
            out.getRentedMovies().size();
            return out;
        } catch(Exception e) {
            throw new DAOException(e);
        }
    }

    public Collection<Order> retrieveAll() throws DAOException {
        try {
            return new ArrayList<Order>(em.createNamedQuery(Order.RETRIEVE_ALL, Order.class)
                                        .getResultList());
        } catch(Exception e) {
            throw new DAOException(e);
        }
    }

    public Collection<Order> retrieveAllBetween(LocalDate from, LocalDate to) throws DAOException {
        try {
            return new ArrayList<Order>(em.createNamedQuery(Order.RETRIEVE_ALL_BETWEEN_DATES, Order.class)
                                        .setParameter("from", from)
                                        .setParameter("to", to)
                                        .getResultList());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    public Collection<Order> retrieveAllBetween(LocalDate from, LocalDate to, Integer userID) throws DAOException {
         try {
            return new ArrayList<Order>(em.createNamedQuery(Order.RETRIEVE_ALL_BETWEEN_DATES_BY_USERID, Order.class)
                                        .setParameter("from", from)
                                        .setParameter("to", to)
                                        .setParameter("userID", userID)
                                        .getResultList());
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }
}
