package control.browse;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import control.exceptions.DAOException;
import jakarta.inject.Inject;
import persistence.model.User;
import persistence.service.UserService;

public class UpdateUserServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private UserService userService;

    public UpdateUserServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        request.getRequestDispatcher("/errors/error405.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            User user = (User) request.getSession().getAttribute("user");
            
            String firstName = request.getParameter("firstName").trim();
            String lastName = request.getParameter("lastName").trim();
            String billingAddress = request.getParameter("billingAddress").trim();
            Float credit = Float.parseFloat(request.getParameter("credit").trim());
            
            user.setFirstName(firstName);
            user.setLastName(lastName);
            user.setBillingAddress(billingAddress);
            user.setCredit(credit);
            
            userService.update(user);
            
            response.sendRedirect(request.getContextPath() + "/common/index.jsp");
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
