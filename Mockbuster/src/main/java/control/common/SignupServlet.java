package control.common;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import control.exceptions.DAOException;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import persistence.model.Cart;
import persistence.model.Customer;
import persistence.service.UserService;



//@WebServlet(name = "SignupServlet", urlPatterns = "/common/SignupServlet")
public class SignupServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private UserService userService;

    public SignupServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        request.getRequestDispatcher("/errors/error405.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String password = request.getParameter("password").trim();
            String email = request.getParameter("email").trim();
            String firstName = request.getParameter("firstName").trim();
            String lastName = request.getParameter("lastName").trim();
            String billingAddress = request.getParameter("billingAddress").trim();
            Customer user = new Customer(email, password, firstName, lastName, billingAddress);

            userService.signup(user);
            
            request.getSession().setAttribute("user", user);
            request.getSession().setAttribute("cart", new Cart());
            response.sendRedirect(request.getContextPath() + "/common/index.jsp");
        } catch (NoSuchAlgorithmException | DAOException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
