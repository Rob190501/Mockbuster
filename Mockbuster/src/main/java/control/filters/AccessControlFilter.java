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
import jakarta.servlet.http.HttpSession;
import javax.sql.DataSource;

import control.exceptions.DAOException;
import model.Cart;
import model.User;
import model.dao.UserDAO;

@WebFilter("/AccessControlFilter")
public class AccessControlFilter extends HttpFilter implements Filter {
    private static final long serialVersionUID = 1L;

    public AccessControlFilter() {
        super();
    }

    @Override
    public void destroy() {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        
        httpRequest.setCharacterEncoding("UTF-8");
        
        String targetPage = httpRequest.getServletPath().toLowerCase();
        String indexPage = httpRequest.getContextPath() + "/common/index.jsp";
        
        User user = (User)httpRequest.getSession().getAttribute("user");
        
        if(user != null) {
            if(httpRequest.getSession().getAttribute("cart") == null) {
                httpRequest.getSession().setAttribute("cart", new Cart());
            }
            UserDAO userDAO = new UserDAO((DataSource)httpRequest.getServletContext().getAttribute("DataSource"));
            try {
                httpRequest.getSession().setAttribute("user", userDAO.retrieveByID(user.getId()));
            } catch (DAOException e) {
                httpRequest.getSession().invalidate();
                e.printStackTrace();
                throw new ServletException(e);
            }
        }
        
        if((targetPage.contains("browse") || targetPage.contains("admin")) && user == null) {
            httpResponse.sendRedirect(indexPage);
            return;
        }
        
        if(targetPage.contains("moviepage.jsp") && httpRequest.getAttribute("movie") == null) {
            httpResponse.sendRedirect(indexPage);
            return;
        }
        
        if(targetPage.contains("admin") && isNotAdmin(user)) {
            httpResponse.sendRedirect(indexPage);
            return;
        }
        
        if((targetPage.contains("login") || targetPage.contains("signup")) && user != null) {
            httpResponse.sendRedirect(indexPage);
            return;
        }
        
        chain.doFilter(request, response);
    }

    private Boolean isNotAdmin(User user) {
        return user == null ? Boolean.TRUE : !user.isAdmin();
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
    }
}

