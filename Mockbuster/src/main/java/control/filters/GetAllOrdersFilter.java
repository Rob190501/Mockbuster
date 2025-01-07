package control.filters;

import java.io.IOException;
import java.net.http.HttpRequest;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

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

@WebFilter("/GetAllOrdersFilter")
public class GetAllOrdersFilter extends HttpFilter implements Filter {

    public GetAllOrdersFilter() {
        super();
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        httpRequest.setCharacterEncoding("UTF-8");

        try {
            LocalDate from = LocalDate.parse(httpRequest.getParameter("from").trim(), DateTimeFormatter.ISO_DATE);
            LocalDate to = LocalDate.parse(httpRequest.getParameter("to").trim(), DateTimeFormatter.ISO_DATE);
        } catch (Exception e) {
            httpResponse.sendRedirect(httpRequest.getContextPath() + "/admin/allOrdersPage.jsp");
            return;
        }

        chain.doFilter(request, response);
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }
}
