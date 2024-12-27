package control.admin;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collection;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.sql.DataSource;


import control.exceptions.DAOException;
import model.Order;
import model.User;
import model.dao.OrderDAO;
import model.dao.UserDAO;

public class GetAllOrdersServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public GetAllOrdersServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderDAO orderDAO = new OrderDAO((DataSource)getServletContext().getAttribute("DataSource"));
		UserDAO userDAO = new UserDAO((DataSource)getServletContext().getAttribute("DataSource"));
		
		if(request.getParameter("from") == null || request.getParameter("to") == null) {
			try {
				Collection<Order> orders = orderDAO.retrieveAll();
				Collection<User> users = userDAO.retrieveAll();
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
		} catch (Exception e) {
			userID = null;
		}
		
		try {
			Collection<Order> orders = userID == null? orderDAO.retrieveAllBetween(from, to) :
													   orderDAO.retrieveAllBetween(from, to, userID);
			Collection<User> users = userDAO.retrieveAll();
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
