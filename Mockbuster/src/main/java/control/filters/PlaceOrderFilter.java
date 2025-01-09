package control.filters;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.util.ArrayList;

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

import persistence.model.Cart;
import persistence.model.User;

public class PlaceOrderFilter extends HttpFilter implements Filter {

    public PlaceOrderFilter() {
        super();
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        httpRequest.setCharacterEncoding("UTF-8");

        Cart cart = (Cart) httpRequest.getSession().getAttribute("cart");
        User user = (User) httpRequest.getSession().getAttribute("user");

        String cartPath = httpRequest.getContextPath() + "/browse/cartPage.jsp";
        ArrayList<String> errors = new ArrayList<String>();

        if (cart.isEmpty()) {
            httpResponse.sendRedirect(cartPath);
            return;
        }

        if (!user.isAdmin()) {
            if (user.getCredit() < cart.getTotal()) {
                errors.add("Credito non sufficiente");
                httpRequest.setAttribute("errors", errors);
                httpRequest.getRequestDispatcher("/browse/cartPage.jsp").forward(httpRequest, httpResponse);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }
}
