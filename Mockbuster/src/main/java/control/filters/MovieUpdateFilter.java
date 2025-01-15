package control.filters;

import java.io.IOException;
import java.util.ArrayList;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;



public class MovieUpdateFilter extends HttpFilter implements Filter {
    
    public MovieUpdateFilter() {
        super();
    }

    public void destroy() {
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;

        String method = httpRequest.getMethod().toLowerCase();

        if (method.equals("get")) {
            String id = httpRequest.getParameter("movieid");
            if (!isValidID(id)) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/common/index.jsp");
                return;
            }
        }

        if (method.equals("post")) {

            String id = httpRequest.getParameter("movieid");
            if (!isValidID(id)) {
                httpResponse.sendRedirect(httpRequest.getContextPath() + "/common/index.jsp");
                return;
            }

            String title = httpRequest.getParameter("title");
            String plot = httpRequest.getParameter("plot");
            String duration = httpRequest.getParameter("duration");
            String year = httpRequest.getParameter("year");
            String availableLicenses = httpRequest.getParameter("availableLicenses");
            String dailyRentalPrice = httpRequest.getParameter("dailyRentalPrice");
            String purchasePrice = httpRequest.getParameter("purchasePrice");
            ArrayList<String> errors = new ArrayList<>();

            if (!isValidText(title)) {
                errors.add("Titolo non valido. Consentite solo max 200 lettere, numeri e spazi.");
            }
            if (!isValidText(plot)) {
                errors.add("Trama non valida. Consentite solo max 200 lettere, numeri e spazi.");
            }
            if (!isValidInteger(duration)) {
                errors.add("Durata non valida. Consentiti solo numeri interi >= 0.");
            }
            if (!isValidYear(year)) {
                errors.add("Anno non valido. Anno min. 1888.");
            }
            if (!isValidInteger(availableLicenses)) {
                errors.add("Numero licenze non valido. Consentiti solo numeri interi >= 0.");
            }
            if (!isValidPrice(dailyRentalPrice)) {
                errors.add("Prezzo Noleggio Giornaliero non valido. Consentiti solo numeri interi >= 0.");
            }
            if (!isValidPrice(purchasePrice)) {
                errors.add("Prezzo di acquisto non valido. Consentiti solo numeri interi >= 0.");
            }

            if (!errors.isEmpty()) {
                httpRequest.setAttribute("movieid", id);
                httpRequest.setAttribute("errors", errors);
                httpRequest.getRequestDispatcher("/admin/MovieUpdateServlet").forward(request, response);
                return;
            }
        }

        chain.doFilter(request, response);
    }

    public boolean isValidID(String id) {
        try {
            Integer.parseInt(id.trim());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isValidText(String text) {
        String regex = "[\\w\\sàèìòù.,']{1,200}";
        return text == null ? Boolean.FALSE : text.trim().matches(regex);
    }

    public boolean isValidInteger(String integer) {
        try {
            return Integer.parseInt(integer.trim()) >= 0;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isValidYear(String year) {
        try {
            return Integer.parseInt(year.trim()) >= 1888;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isValidPrice(String price) {
        try {
            return Float.parseFloat(price.trim()) >= 0;
        } catch (Exception e) {
            return false;
        }
    }

    public void init(FilterConfig fConfig) throws ServletException {
    }
}
