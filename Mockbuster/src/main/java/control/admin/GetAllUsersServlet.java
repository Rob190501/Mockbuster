package control.admin;

import java.io.IOException;
import java.util.Collection;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import control.exceptions.DAOException;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import persistence.model.Customer;
import persistence.service.UserService;



//@WebServlet(name = "GetAllUsersServlet", urlPatterns = {"/admin/GetAllUsersServlet"})
public class GetAllUsersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private UserService userService;

    public GetAllUsersServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Collection<Customer> users = userService.retrieveAll();
            request.setAttribute("users", users);
            request.getRequestDispatcher("/admin/allUsersPage.jsp").forward(request, response);
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
