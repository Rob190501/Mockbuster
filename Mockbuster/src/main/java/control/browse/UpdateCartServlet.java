package control.browse;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import control.exceptions.DAOException;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import persistence.model.Cart;
import persistence.service.CartService;



//@WebServlet(name = "UpdateCartServlet", urlPatterns = {"/browse/UpdateCartServlet"})
public class UpdateCartServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Inject 
    private CartService cartService;
    
    public UpdateCartServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action").trim();
        Cart cart = (Cart) request.getSession().getAttribute("cart");

        if(action.equals("empty")) {
            cart.empty();
        } else {
            Integer movieID = Integer.parseInt(request.getParameter("movieid").trim());

            if(action.equals("add")) {
                try {
                    String type = request.getParameter("type").trim();
                    
                    if(type.equals("rent")) {
                        Integer days = Integer.parseInt(request.getParameter("days").trim());
                        cartService.addRent(cart, movieID, days);
                    }
                    
                    if(type.equals("purchase")) {
                        cartService.addPurchase(cart, movieID);
                    }
                } catch (DAOException e) {
                    e.printStackTrace();
                    throw new ServletException(e);
                }
            }
            
            if(action.equals("remove")) {
                cart.removeFromCart(movieID);
            }
            
            if(action.equals("increasedays")) {
                cart.increaseRentDays(movieID);
            }
            
            if(action.equals("decreasedays")) {
                cart.decreaseRentDays(movieID);
            }
        }
        
        response.sendRedirect(request.getContextPath() + "/browse/cartPage.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
