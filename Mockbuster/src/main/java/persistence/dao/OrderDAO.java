package persistence.dao;

import java.time.LocalDate;

import java.util.Collection;
import control.exceptions.DAOException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import java.util.ArrayList;
import persistence.model.Order;

@Stateless
public class OrderDAO {
    
    @Inject
    private EntityManager em;
    
    public void save(Order order) throws DAOException {
        try {
            em.persist(order);
        } catch(Exception e) {
            throw new DAOException(e);
        }
    }

    public Collection<Order> retrieveByUser(Integer userID) throws DAOException {
        try {
            return new ArrayList<Order>(em.createNamedQuery(Order.RETRIEVE_BY_USERID, Order.class)
                                        .setParameter("userID", userID)
                                        .getResultList());
        } catch(Exception e) {
            throw new DAOException(e);
        }
    }

    public Order retrieveOrderDetails(Integer orderID, Integer userID) throws DAOException {
        try {
            Order out = em.createNamedQuery(Order.RETRIEVE_BY_ID_AND_USERID, Order.class)
                          .setParameter("id", orderID)
                          .setParameter("userID", userID)
                          .getSingleResult();
            out.getPurchasedMovies().size();
            out.getRentedMovies().size();
            return out;
        } catch(NoResultException e) {
            return null;
        } catch(Exception e) {
            throw new DAOException(e);
        }
    }
    
    public Order retrieveOrderDetails(Integer orderID) throws DAOException {
        try {
            Order out = em.find(Order.class, orderID);
            if(out != null) {
                out.getPurchasedMovies().size();
                out.getRentedMovies().size();
            }
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
