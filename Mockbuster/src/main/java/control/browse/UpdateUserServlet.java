package control.browse;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import control.exceptions.DAOException;
import model.Cart;
import model.User;
import model.dao.UserDAO;

public class UpdateUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public UpdateUserServlet() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		request.getRequestDispatcher("/errors/error405.jsp").forward(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			User user = (User)request.getSession().getAttribute("user");
			String firstName = request.getParameter("firstName").trim();
			String lastName = request.getParameter("lastName").trim();
			String billingAddress = request.getParameter("billingAddress").trim();
			Float credit = Float.parseFloat(request.getParameter("credit").trim());
			
			User update = new User(user.getId(),
								   user.getEmail(), 
								   user.getPassword(), 
								   firstName, 
								   lastName, 
								   billingAddress, 
								   credit,
								   user.isAdmin());
			
			UserDAO userDAO = new UserDAO((DataSource)getServletContext().getAttribute("DataSource"));
		
			userDAO.updateUser(update);
			
			request.getSession().setAttribute("user", update);
			response.sendRedirect(request.getContextPath() + "/common/index.jsp");
		} catch (DAOException e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}
}
