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
import persistence.model.Cart;
import persistence.model.User;
import persistence.service.UserService;

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
            User user = userService.login(email, password);

            if (user == null) {
                ArrayList<String> errors = new ArrayList<>();
                errors.add("Username e/o password errati");
                request.setAttribute("errors", errors);
                request.getRequestDispatcher("/common/login.jsp").forward(request, response);
                return;
            }

            HttpSession session = request.getSession();
            session.setAttribute("user", user);
            session.setAttribute("cart", new Cart());
            response.sendRedirect(request.getContextPath() + "/common/index.jsp");
        } catch (NoSuchAlgorithmException | DAOException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }
}
