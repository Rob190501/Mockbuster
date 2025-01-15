package control.filters;

import java.io.IOException;
import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import persistence.model.Customer;
import security.UserRole.Role;



public class GetOrdersFilter extends HttpFilter implements Filter {

    public GetOrdersFilter() {
        super();
    }

    public void destroy() {

    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        try {
            Integer userID = Integer.parseInt(httpRequest.getParameter("userid").trim());
            Integer orderID = Integer.parseInt(httpRequest.getParameter("orderid").trim());
            
            Role role = (Role) httpRequest.getSession().getAttribute("role");
            
            if(role == role.CUSTOMER) {
                Customer user = (Customer) httpRequest.getSession().getAttribute("user");
            
                if (!user.getId().equals(userID)) {
                    throw new Exception();
                }
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
