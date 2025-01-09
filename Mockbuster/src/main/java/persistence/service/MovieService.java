/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence.service;

import control.exceptions.DAOException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.Collection;
import persistence.dao.MovieDAO;
import persistence.model.Movie;
import persistence.model.PurchasedMovie;
import persistence.model.RentedMovie;

/**
 *
 * @author roberto
 */
@Stateless
public class MovieService {
    @Inject
    private MovieDAO movieDAO;
    
    public Collection<Movie> retrieveAll() throws DAOException {
        return movieDAO.retrieveAll();
    }
    
    public Movie retrieveByID(Integer id) throws DAOException {
        return movieDAO.retrieveByID(id);
    }
    
    public Collection<Movie> retrieveByTitle(String title) throws DAOException {
        return movieDAO.retrieveByTitle(title);
    }
    
    public void upload(Movie movie) throws DAOException {
        movieDAO.save(movie);
    }
    
    public void update(Movie movie) throws DAOException {
        movieDAO.update(movie);
    }
    
    public void rentMovie(RentedMovie rm) throws DAOException {
        Movie movie = rm.getMovie();
        movie.deductAvailableLicences(rm.getDays());
        movieDAO.update(movie);
    }
    
    public void purchaseMovie(PurchasedMovie pm) throws DAOException {
        Movie movie = pm.getMovie();
        movie.deductAvailableLicences(1);
        movieDAO.update(movie);
    }
    
}
