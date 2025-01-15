package control.browse;

import java.io.IOException;
import java.util.Collection;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import control.exceptions.DAOException;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import persistence.model.Order;
import persistence.model.Customer;
import persistence.service.OrderService;
import security.UserRole.Role;



//@WebServlet(name = "GetOrdersServlet", urlPatterns = {"/browse/GetOrdersServlet"})
public class GetOrdersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private OrderService orderService;
    
    public GetOrdersServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Role role = (Role) request.getSession().getAttribute("role");
        
        if(role != Role.ORDER_MANAGER) {
            Customer user = (Customer) request.getSession().getAttribute("user");
            
            if (request.getParameter("userid") == null || request.getParameter("orderid") == null) {
                try {
                    Collection<Order> orders = orderService.retrieveByUser(user);
                    request.setAttribute("orders", orders);
                    request.getRequestDispatcher("/browse/ordersPage.jsp").forward(request, response);
                    return;
                } catch (DAOException e) {
                    e.printStackTrace();
                    throw new ServletException();
                }
            }
        }

        Integer orderID = Integer.parseInt(request.getParameter("orderid"));

        try {
            Order orderDetails = orderService.retrieveOrderDetails(orderID);
            
            if(orderDetails == null) {
                response.sendRedirect(request.getContextPath() + "/browse/GetOrdersServlet");
                return;
            }
            
            request.setAttribute("order", orderDetails);
            request.getRequestDispatcher("/browse/orderDetailsPage.jsp").forward(request, response);
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
