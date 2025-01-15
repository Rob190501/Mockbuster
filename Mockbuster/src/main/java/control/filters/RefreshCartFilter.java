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
import control.exceptions.DAOException;
import jakarta.inject.Inject;
import persistence.model.Cart;
import persistence.model.Customer;
import persistence.service.CartService;



public class RefreshCartFilter extends HttpFilter implements Filter {
    
    @Inject
    private CartService cartService;
    
    public RefreshCartFilter() {
        super();
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;

        Cart cart = (Cart) httpRequest.getSession().getAttribute("cart");
        Customer user = (Customer) httpRequest.getSession().getAttribute("user");

        if (user != null && cart == null) {
            cart = new Cart();
            httpRequest.getSession().setAttribute("cart", cart);
        }

        try {
            cartService.refreshCart(cart);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }
}
