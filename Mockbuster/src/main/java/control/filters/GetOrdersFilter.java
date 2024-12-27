package control.filters;

import java.io.IOException;
import java.net.http.HttpRequest;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import model.User;

@WebFilter("/GetOrdersFilter")
public class GetOrdersFilter extends HttpFilter implements Filter {
    
    public GetOrdersFilter() {
        super();
    }

	public void destroy() {

	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse) response;
		
		httpRequest.setCharacterEncoding("UTF-8");
		
		User user = (User) httpRequest.getSession().getAttribute("user");
		
		try {
			Integer userID = Integer.parseInt(httpRequest.getParameter("userid").trim());
			Integer orderID = Integer.parseInt(httpRequest.getParameter("orderid").trim());
			
			if(!user.isAdmin() && !user.getId().equals(userID)) {
				throw new Exception();
			}
		} catch (Exception e) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/browse/ordersPage.jsp");
			return;
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
