package control.browse;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import control.exceptions.DAOException;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import persistence.model.Movie;
import persistence.service.MovieService;
import security.UserRole.Role;



//@WebServlet(name = "MoviePageServlet", urlPatterns = {"/browse/MoviePageServlet"})
public class MoviePageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private MovieService movieService;

    public MoviePageServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id").trim());
        Role role = (Role) request.getSession().getAttribute("role");

        try {
            Movie movie = movieService.retrieveByID(id);
            if (movie == null || (role != Role.CATALOG_MANAGER && !movie.isVisible())) {
                response.sendRedirect(request.getContextPath() + "/common/index.jsp");
                return;
            }

            request.setAttribute("movie", movie);
            request.getRequestDispatcher("/browse/moviePage.jsp").forward(request, response);
            return;
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
