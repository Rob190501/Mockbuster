package persistence.dao;

import java.util.ArrayList;
import java.util.Collection;

import control.exceptions.DAOException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import persistence.model.Customer;

@Stateless
public class UserDAO {

    @Inject
    private EntityManager em;

    public void save(Customer user) throws DAOException {
        try {
            em.persist(user);
        } catch(Exception e) {
            throw new DAOException(e);
        }
    }

    public Customer retrieveByID(int id) throws DAOException {
        try {
            return em.find(Customer.class, id);
        } catch(Exception e) {
            throw new DAOException(e);
        }
    }
    
    public <T>T retrieveByEmailAndPassword(String email, String password, Class<T> entityClass) throws DAOException {
        try {
            String queryName = entityClass.getSimpleName() + ".RETRIEVE_BY_EMAIL_AND_PSW";
            return em.createNamedQuery(queryName, entityClass)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch (NoResultException e) {
            return null;
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    public Boolean checkEmailAvailability(String email) throws DAOException {
        try {
            Long count = em.createNamedQuery(Customer.CHECK_EMAIL_AVAILABILITY, Long.class)
                           .setParameter("email", email)
                           .getSingleResult();
            return count == 0;
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    public Collection<Customer> retrieveAll() throws DAOException {
        try {
            return new ArrayList<Customer>(em.createNamedQuery(Customer.RETRIEVE_ALL, Customer.class)
                                        .getResultList());
        } catch(Exception e) {
            throw new DAOException(e);
        }
    }

    public void update(Customer user) throws DAOException {
        try {
            em.merge(user);
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    public void setAdmin(Integer id, Boolean isAdmin) throws DAOException {
    }

}
