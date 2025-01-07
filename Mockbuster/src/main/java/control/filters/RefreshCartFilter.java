package control.filters;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import control.exceptions.DAOException;
import jakarta.inject.Inject;
import model.Cart;
import model.Movie;
import model.PurchasedMovie;
import model.RentedMovie;
import model.User;
import model.dao.MovieDAO;

public class RefreshCartFilter extends HttpFilter implements Filter {
    
    @Inject
    private MovieDAO movieDAO;
    
    public RefreshCartFilter() {
        super();
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        httpRequest.setCharacterEncoding("UTF-8");

        Cart cart = (Cart) httpRequest.getSession().getAttribute("cart");
        User user = (User) httpRequest.getSession().getAttribute("user");
        //MovieDAO movieDAO = new MovieDAO((DataSource) httpRequest.getServletContext().getAttribute("DataSource"));

        if (user != null && cart == null) {
            cart = new Cart();
            httpRequest.getSession().setAttribute("cart", cart);
        }

        try {
            Collection<RentedMovie> refreshedRentedMovies = new ArrayList<RentedMovie>();
            for (RentedMovie movie : cart.getRentedMovies()) {
                Movie refreshed = movieDAO.retrieveByID(movie.getMovie().getId());
                if (refreshed != null) {
                    if ((movie.getDays() >= 1 && refreshed.getAvailableLicenses() >= movie.getDays()) && refreshed.isVisible()) {
                        refreshedRentedMovies.add(new RentedMovie(refreshed, refreshed.getDailyRentalPrice(), movie.getDays()));
                    }
                }
            }
            cart.setRentedMovies(refreshedRentedMovies);

            Collection<PurchasedMovie> refreshedPurchasedMovies = new ArrayList<PurchasedMovie>();
            for (PurchasedMovie movie : cart.getPurchasedMovies()) {
                Movie refreshed = movieDAO.retrieveByID(movie.getMovie().getId());
                if (refreshed != null) {
                    if (refreshed.getAvailableLicenses() >= 1 && refreshed.isVisible()) {
                        refreshedPurchasedMovies.add(new PurchasedMovie(refreshed, refreshed.getPurchasePrice()));
                    }
                }
            }
            cart.setPurchasedMovies(refreshedPurchasedMovies);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }
}
