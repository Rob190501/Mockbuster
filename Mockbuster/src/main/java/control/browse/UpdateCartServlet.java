package control.browse;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import control.exceptions.DAOException;
import jakarta.inject.Inject;
import model.Cart;
import model.Movie;
import model.PurchasedMovie;
import model.RentedMovie;
import model.dao.MovieDAO;

public class UpdateCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private MovieDAO movieDAO;
    
    public UpdateCartServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action").trim();
        Cart cart = (Cart) request.getSession().getAttribute("cart");

        if (action.equals("empty")) {
            cart.empty();
        } else {
            Integer movieID = Integer.parseInt(request.getParameter("movieid").trim());

            if (action.equals("add")) {
                addToCart(request, response);
            }
            if (action.equals("remove")) {
                cart.removeFromCart(movieID);
            }
            if (action.equals("increasedays")) {
                cart.increaseRentDays(movieID);
            }
            if (action.equals("decreasedays")) {
                cart.decreaseRentDays(movieID);
            }
        }
        response.sendRedirect(request.getContextPath() + "/browse/cartPage.jsp");
    }

    private void addToCart(HttpServletRequest request, HttpServletResponse response) throws ServletException {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        //MovieDAO movieDAO = new MovieDAO((DataSource) request.getServletContext().getAttribute("DataSource"));

        String type = request.getParameter("type").trim();
        Integer movieID = Integer.parseInt(request.getParameter("movieid").trim());

        try {
            if (!cart.purchasesContains(movieID) && !cart.rentsContains(movieID)) {
                Movie movie = movieDAO.retrieveByID(movieID);

                if (movie != null && movie.isVisible()) {
                    if (type.equals("rent")) {
                        Integer days = Integer.parseInt(request.getParameter("days").trim());
                        RentedMovie rentedMovie = new RentedMovie(movie, movie.getDailyRentalPrice(), days);
                        if (days >= 1 && days <= rentedMovie.getMovie().getAvailableLicenses()) {
                            cart.addRentedMovie(rentedMovie);
                        }
                        return;
                    }

                    if (type.equals("purchase")) {
                        PurchasedMovie purchasedMovie = new PurchasedMovie(movie, movie.getPurchasePrice());
                        if (purchasedMovie.getMovie().getAvailableLicenses() >= 1) {
                            cart.addPurchasedMovie(purchasedMovie);
                        }
                        return;
                    }
                }
            }
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
