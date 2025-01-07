package model.dao;

import java.util.Collection;
import control.exceptions.DAOException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import model.Movie;

@Stateless
public class MovieDAO {
    
    @Inject
    private EntityManager em;
    
    public void save(Movie bean) throws DAOException {
        try {
            em.persist(bean);
        } catch(Exception e) {
            throw new DAOException(e);
        }
    }

    public Movie retrieveByID(int id) throws DAOException {
        try {
            return em.find(Movie.class, id);
        } catch(Exception e) {
            throw new DAOException(e);
        }
    }

    public Collection<Movie> retrieveByTitle(String title) throws DAOException {
        try {
            return new ArrayList<Movie>(em.createNamedQuery(Movie.RETRIEVE_BY_TITLE, Movie.class)
                                        .setParameter("title", "%" + title + "%")
                                        .getResultList());
        } catch(Exception e) {
            throw new DAOException(e);
        }
    }

    public Collection<Movie> retrieveAll() throws DAOException {
        try {
            return new ArrayList<Movie>(em.createNamedQuery(Movie.RETRIEVE_ALL, Movie.class)
                                        .getResultList());
        } catch(Exception e) {
            throw new DAOException(e);
        }
    }

    //sostituire con merge
    public void update(Movie bean) throws DAOException {
        try {
            Movie movie = em.find(Movie.class, bean.getId());
            movie.setTitle(bean.getTitle());
            movie.setPlot(bean.getPlot());
            movie.setDuration(bean.getDuration());
            movie.setYear(bean.getYear());
            movie.setAvailableLicenses(bean.getAvailableLicenses());
            movie.setDailyRentalPrice(bean.getDailyRentalPrice());
            movie.setPurchasePrice(bean.getPurchasePrice());
            movie.setVisible(bean.isVisible());
        } catch(Exception e) {
            throw new DAOException(e);
        }
    }
}
