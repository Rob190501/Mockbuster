package persistence.dao;

import java.util.Collection;
import control.exceptions.DAOException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import java.util.ArrayList;
import persistence.model.Movie;

@Stateless
public class MovieDAO {
    
    @Inject
    private EntityManager em;
    
    public void save(Movie movie) throws DAOException {
        try {
            em.persist(movie);
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
    public void update(Movie movieUpdates) throws DAOException {
        try {
            Movie movie = em.find(Movie.class, movieUpdates.getId());
            movie.setTitle(movieUpdates.getTitle());
            movie.setPlot(movieUpdates.getPlot());
            movie.setDuration(movieUpdates.getDuration());
            movie.setYear(movieUpdates.getYear());
            movie.setAvailableLicenses(movieUpdates.getAvailableLicenses());
            movie.setDailyRentalPrice(movieUpdates.getDailyRentalPrice());
            movie.setPurchasePrice(movieUpdates.getPurchasePrice());
            movie.setVisible(movieUpdates.isVisible());
        } catch(Exception e) {
            throw new DAOException(e);
        }
    }
}
