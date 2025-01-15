package control.common;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import control.exceptions.DAOException;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import persistence.model.CatalogManager;
import persistence.model.Customer;
import persistence.model.OrderManager;
import persistence.service.UserService;
import security.UserRole.Role;



//@WebServlet(name = "LoginServlet", urlPatterns = "/common/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private UserService userService;
    
    public LoginServlet() {
        super();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        request.getRequestDispatcher("/errors/error405.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email").trim();
        String password = request.getParameter("password").trim();

        try {
            HttpSession session = request.getSession();
            
            Customer customer = userService.customerLogin(email, password);
            if (customer != null) {
                session.setAttribute("user", customer);
                session.setAttribute("role", Role.CUSTOMER);
                response.sendRedirect(request.getContextPath() + "/common/index.jsp");
                return;
            }

            CatalogManager catalogManager = userService.catalogManagerLogin(email, password);
            if (catalogManager != null) {
                session.setAttribute("user", catalogManager);
                session.setAttribute("role", Role.CATALOG_MANAGER);
                response.sendRedirect(request.getContextPath() + "/common/index.jsp");
                return;
            }

            OrderManager orderManager = userService.orderManagerLogin(email, password);
            if (orderManager != null) {
                session.setAttribute("user", orderManager);
                session.setAttribute("role", Role.ORDER_MANAGER);
                response.sendRedirect(request.getContextPath() + "/admin/allOrdersPage.jsp");
                return;
            }
            
            ArrayList<String> errors = new ArrayList<>();
            errors.add("Username e/o password errati");
            request.setAttribute("errors", errors);
            request.getRequestDispatcher("/common/login.jsp").forward(request, response);
        } catch (NoSuchAlgorithmException | DAOException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
