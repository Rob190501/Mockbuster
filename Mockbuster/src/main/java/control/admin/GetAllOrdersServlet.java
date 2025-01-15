package control.admin;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import persistence.service.UserService;



//@WebServlet(name = "GetAllOrdersServlet", urlPatterns = {"/admin/GetAllOrdersServlet"})
public class GetAllOrdersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private OrderService orderService;
    @Inject
    private UserService userService;

    public GetAllOrdersServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("from") == null || request.getParameter("to") == null) {
            try {
                Collection<Order> orders = orderService.retrieveAll();
                Collection<Customer> users = userService.retrieveAllCustomers();
                request.setAttribute("orders", orders);
                request.setAttribute("users", users);
                request.getRequestDispatcher("/admin/allOrdersPage.jsp").forward(request, response);
                return;
            } catch (DAOException e) {
                e.printStackTrace();
                throw new ServletException(e);
            }
        }

        LocalDate from = LocalDate.parse(request.getParameter("from").trim(), DateTimeFormatter.ISO_DATE);
        LocalDate to = LocalDate.parse(request.getParameter("to").trim(), DateTimeFormatter.ISO_DATE);
        Integer userID;
        try {
            userID = Integer.parseInt(request.getParameter("userid").trim());
        } catch(Exception e) {
            userID = null;
        }

        try {
            Collection<Customer> users = userService.retrieveAllCustomers();
            Collection<Order> orders = orderService.retrieveAllBetween(from, to, userID);
            
            request.setAttribute("orders", orders);
            request.setAttribute("users", users);
            request.getRequestDispatcher("/admin/allOrdersPage.jsp").forward(request, response);
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
