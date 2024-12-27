package control.browse;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import control.exceptions.DAOException;
import model.Cart;
import model.Order;
import model.User;
import model.dao.OrderDAO;
import model.dao.UserDAO;

public class PlaceOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
    public PlaceOrderServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Cart cart = (Cart)request.getSession().getAttribute("cart");
		User user = (User)request.getSession().getAttribute("user");
		
		Order order = new Order(user);
		order.setPurchasedMovies(cart.getPurchasedMovies());
		order.setRentedMovies(cart.getRentedMovies());
		order.setTotal(cart.getTotal());
		
		OrderDAO orderDAO = new OrderDAO((DataSource)request.getServletContext().getAttribute("DataSource"));
		
		try {
			orderDAO.placeOrder(order);
			cart.empty();
			response.sendRedirect(request.getContextPath() + "/browse/GetOrdersServlet?userid=" + user.getId() + "&orderid=" + order.getId());
			return;
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
}
