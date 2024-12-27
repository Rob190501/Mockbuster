package control.filters;

import java.io.IOException;
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

@WebFilter("/MovieRetrieveFilter")
public class MovieRetrieveFilter extends HttpFilter implements Filter {
	
    public MovieRetrieveFilter() {
        super();
    }

	public void destroy() {
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest httpRequest = (HttpServletRequest)request;
		HttpServletResponse httpResponse = (HttpServletResponse)response;
		
		try {
			String page = httpRequest.getParameter("page").trim();
			
			if(!page.equals("index") && !page.equals("notvisible")) {
				throw new Exception();
			}
			
			if(page.equals("notvisible")) {
				User user = (User)httpRequest.getSession().getAttribute("user");
				if(user == null || !user.isAdmin()) {
					throw new Exception();
				}
			}
		} catch(Exception e) {
			httpResponse.sendRedirect(httpRequest.getContextPath() + "/common/index.jsp");
			return;
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
