package control.admin;

import java.io.IOException;
import java.util.Collection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import control.exceptions.DAOException;
import jakarta.inject.Inject;
import model.dao.UserDAO;
import model.User;

public class GetAllUsersServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private UserDAO userDAO;

    public GetAllUsersServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Collection<User> users = userDAO.retrieveAll();
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
