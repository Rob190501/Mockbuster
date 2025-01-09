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
import persistence.model.Cart;
import persistence.model.Order;
import persistence.model.User;
import persistence.dao.OrderDAO;
import persistence.dao.UserDAO;
import persistence.service.OrderService;

public class PlaceOrderServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private OrderService orderService;
    
    public PlaceOrderServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Cart cart = (Cart) request.getSession().getAttribute("cart");
        User user = (User) request.getSession().getAttribute("user");

        try {
            Order order = orderService.placeOrder(user, cart);
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
