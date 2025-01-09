package control.browse;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import control.exceptions.DAOException;
import jakarta.inject.Inject;
import persistence.model.Movie;
import persistence.model.User;
import persistence.service.MovieService;

public class MoviePageServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private MovieService movieService;

    public MoviePageServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer id = Integer.parseInt(request.getParameter("id").trim());
        User user = (User) request.getSession().getAttribute("user");

        try {
            Movie movie = movieService.retrieveByID(id);
            if (movie == null || (!user.isAdmin() && !movie.isVisible())) {
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
