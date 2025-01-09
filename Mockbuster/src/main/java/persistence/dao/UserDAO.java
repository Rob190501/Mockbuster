package persistence.dao;

import java.util.ArrayList;
import java.util.Collection;

import control.exceptions.DAOException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import persistence.model.User;

@Stateless
public class UserDAO {

    @Inject
    private EntityManager em;

    public void save(User user) throws DAOException {
        try {
            em.persist(user);
        } catch(Exception e) {
            throw new DAOException(e);
        }
    }

    public User retrieveByID(int id) throws DAOException {
        try {
            return em.find(User.class, id);
        } catch(Exception e) {
            throw new DAOException(e);
        }
    }

    public User retrieveByEmailAndPassword(String email, String password) throws DAOException {
        try {
            return em.createNamedQuery(User.RETRIEVE_BY_EMAIL_AND_PSW, User.class)
                    .setParameter("email", email)
                    .setParameter("password", password)
                    .getSingleResult();
        } catch(Exception e) {
            throw new DAOException(e);
        }
    }

    public Boolean checkEmailAvailability(String email) throws DAOException {
        try {
            Long count = em.createNamedQuery(User.CHECK_EMAIL_AVAILABILITY, Long.class)
                           .setParameter("email", email)
                           .getSingleResult();
            return count == 0;
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    public Collection<User> retrieveAll() throws DAOException {
        try {
            return new ArrayList<User>(em.createNamedQuery(User.RETRIEVE_ALL, User.class)
                                        .getResultList());
        } catch(Exception e) {
            throw new DAOException(e);
        }
    }

    public void update(User user) throws DAOException {
        try {
            em.merge(user);
        } catch (Exception e) {
            throw new DAOException(e);
        }
    }

    public void setAdmin(Integer id, Boolean isAdmin) throws DAOException {
    }

}
