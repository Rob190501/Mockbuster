package control.filters;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import control.exceptions.DAOException;
import model.User;
import model.dao.UserDAO;

@WebFilter("/SignupFormFilter")
public class UpdateUserFilter extends HttpFilter implements Filter {
       
    private static final long serialVersionUID = 1L;

    public UpdateUserFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest) request;
		httpRequest.setCharacterEncoding("UTF-8");
		User user = (User)httpRequest.getSession().getAttribute("user");
		
		String firstName = httpRequest.getParameter("firstName");
		String lastName = httpRequest.getParameter("lastName");
		String billingAddress = httpRequest.getParameter("billingAddress");
		String credit = httpRequest.getParameter("credit");
		
		RequestDispatcher dispatcher = httpRequest.getRequestDispatcher("/browse/myAccount.jsp");
		ArrayList<String> errors = new ArrayList<>();
		
		if(!isValidName(firstName)) {
			errors.add("Nome non valido");
		}
		
		if(!isValidName(lastName)) {
			errors.add("Cognome non valido");
		}
		
		if(!isValidBillingAddress(billingAddress)) {
			errors.add("Indirizzo non valido");
		}
		
		if(!isValidCredit(credit, user.getCredit())) {
			errors.add("Saldo non valido");
		}
		
		if(!errors.isEmpty()) {
			httpRequest.setAttribute("errors", errors);
			dispatcher.forward(request, response);
			return;
		}
		
		chain.doFilter(request, response);
	}
	
	private static boolean isValidName(String name) {
        String regex = "[A-Za-z]+";
        return name == null ? Boolean.FALSE : name.trim().matches(regex);
    }
	
	private static boolean isValidBillingAddress(String address) {
        String regex = "([A-Za-z]+\\s)+\\d+\\s\\d{5}\\s[A-Za-z]+";
        return address == null ? Boolean.FALSE : address.trim().matches(regex);
    }
	
	private static boolean isValidCredit(String newCredit, Float oldCredit) {
		try {
			return Float.parseFloat(newCredit.trim()) >= oldCredit;
		} catch(Exception e) {
			return false;
		}
	}

	public void init() throws ServletException {
	}

}
