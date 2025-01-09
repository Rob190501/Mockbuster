/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package persistence.service;

import control.exceptions.DAOException;
import jakarta.ejb.Stateless;
import jakarta.inject.Inject;
import java.util.ArrayList;
import java.util.Collection;
import persistence.model.Cart;
import persistence.model.Movie;
import persistence.model.PurchasedMovie;
import persistence.model.RentedMovie;

/**
 *
 * @author roberto
 */
@Stateless
public class CartService {
    
    @Inject
    private MovieService movieService;
    
    public void refreshCart(Cart cart) throws DAOException {
        Collection<RentedMovie> refreshedRentedMovies = new ArrayList<RentedMovie>();
        for(RentedMovie movie : cart.getRentedMovies()) {
            Movie refreshed = movieService.retrieveByID(movie.getMovie().getId());
            
            if(refreshed != null) {
                if((movie.getDays() >= 1 && refreshed.getAvailableLicenses() >= movie.getDays()) && refreshed.isVisible()) {
                    refreshedRentedMovies.add(new RentedMovie(refreshed, refreshed.getDailyRentalPrice(), movie.getDays()));
                }
            }
        }
        cart.setRentedMovies(refreshedRentedMovies);

        Collection<PurchasedMovie> refreshedPurchasedMovies = new ArrayList<PurchasedMovie>();
        for(PurchasedMovie movie : cart.getPurchasedMovies()) {
            Movie refreshed = movieService.retrieveByID(movie.getMovie().getId());
            
            if(refreshed != null) {
                if(refreshed.getAvailableLicenses() >= 1 && refreshed.isVisible()) {
                    refreshedPurchasedMovies.add(new PurchasedMovie(refreshed, refreshed.getPurchasePrice()));
                }
            }
        }
        cart.setPurchasedMovies(refreshedPurchasedMovies);
    }
    
    public void addRent(Cart cart, Integer movieID, Integer days) throws DAOException {
        if (!cart.purchasesContains(movieID) && !cart.rentsContains(movieID)) {
            Movie movie = movieService.retrieveByID(movieID);

            if (movie != null && movie.isVisible()) {
                
                RentedMovie rentedMovie = new RentedMovie(movie, movie.getDailyRentalPrice(), days);
                
                if (days >= 1 && days <= rentedMovie.getMovie().getAvailableLicenses()) {
                    cart.addRentedMovie(rentedMovie);
                }
            }
        }
    }
    
    public void addPurchase(Cart cart, Integer movieID) throws DAOException {
        if (!cart.purchasesContains(movieID) && !cart.rentsContains(movieID)) {
            Movie movie = movieService.retrieveByID(movieID);
            
            PurchasedMovie purchasedMovie = new PurchasedMovie(movie, movie.getPurchasePrice());
            
            if (purchasedMovie.getMovie().getAvailableLicenses() >= 1) {
                cart.addPurchasedMovie(purchasedMovie);
            }
        }
    }
    
    
}
