package control.common;

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

public class SignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SignupServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		request.getRequestDispatcher("/errors/error405.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		try {
			String password = toHash(request.getParameter("password").trim());
			String email = request.getParameter("email").trim();
			
			String firstName = request.getParameter("firstName").trim();
			String lastName = request.getParameter("lastName").trim();
			String billingAddress = request.getParameter("billingAddress").trim();
			User user = new User(email, password, firstName, lastName, billingAddress);
			
			UserDAO userDAO = new UserDAO((DataSource)getServletContext().getAttribute("DataSource"));
		
			userDAO.save(user);
			request.getSession().setAttribute("user", user);
			request.getSession().setAttribute("cart", new Cart());
			response.sendRedirect(request.getContextPath() + "/common/index.jsp");
		} catch (NoSuchAlgorithmException | DAOException e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}
	
	private String toHash(String password) throws NoSuchAlgorithmException {
		String hashString = null;
		java.security.MessageDigest digest = java.security.MessageDigest.getInstance("SHA-512");
		byte[] hash = digest.digest (password.getBytes(StandardCharsets.UTF_8));
		hashString = "";
		for (byte element : hash) {
			hashString += Integer.toHexString((element & 0xFF) | 0x100).substring(1,3);
		}
		return hashString;
	}

}
