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
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import security.UserRole.Role;



public class AccessControlFilter extends HttpFilter implements Filter {
    private static final long serialVersionUID = 1L;
    
    private Map<Role, List<String>> rolePages;

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
        String allOrdersPage = httpRequest.getContextPath() + "/admin/allOrdersPage.jsp";
        
        Role role = (Role) httpRequest.getSession().getAttribute("role");
        
        if(targetPage.contains("moviepage.jsp") && httpRequest.getAttribute("movie") == null) {
            httpResponse.sendRedirect(indexPage);
            return;
        }
        
        if(isAccessAllowed(role, targetPage)) {
            chain.doFilter(request, response);
            return;
        } else {
            if(role == Role.CUSTOMER || role == Role.CATALOG_MANAGER || role == null) {
                httpResponse.sendRedirect(indexPage);
                return;
            }
            if(role == Role.ORDER_MANAGER) {
                httpResponse.sendRedirect(allOrdersPage);
                return;
            }
        }
    }

    @Override
    public void init(FilterConfig fConfig) throws ServletException {
        rolePages = new HashMap<>();

        // Pagine consentite per CATALOG_MANAGER
        rolePages.put(Role.CATALOG_MANAGER, Arrays.asList(
            "/common/index.jsp",
            "/common/logoutservlet",
            "/browse/moviepageservlet",
            "/browse/searchmovietitleservlet",
            "/admin/movieupdateservlet",
            "/admin/notvisiblepage.jsp",
            "/admin/movieupload.jsp",
            "/admin/movieuploadservlet"
        ));

        // Pagine consentite per ORDER_MANAGER
        rolePages.put(Role.ORDER_MANAGER, Arrays.asList(
            "/admin/allorderspage.jsp",
            "/browse/getordersservlet",
            "/admin/getallordersservlet",
            "/common/logoutservlet"
        ));
    }
    
    private boolean isAccessAllowed(Role role, String targetPage) {
        if(targetPage.startsWith("/styles/") || targetPage.startsWith("/scripts/")) {
            return true;
        }

        if((targetPage.contains("login") || targetPage.contains("signup")) && role != null) {
            return false;
        }
        
        if (role == null) {
            // Consentire l'accesso solo alle pagine sotto /common/
            return targetPage.startsWith("/common/");
        }

        // CUSTOMER può accedere a tutte le pagine eccetto quelle sotto /admin/
        if (role == Role.CUSTOMER) {
            return !targetPage.startsWith("/admin/");
        }

        // Ottieni le pagine consentite per il ruolo
        List<String> allowedPages = rolePages.get(role);

        // Verifica se la pagina richiesta è consentita
        for (String page : allowedPages) {
            if (targetPage.endsWith(page)) {
                return true;
            }
        }
        
        return false;
    }
}

