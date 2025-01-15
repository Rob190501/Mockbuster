package control.common;

import java.io.IOException;
import java.util.Collection;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import control.exceptions.DAOException;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import persistence.model.Movie;
import persistence.service.MovieService;



//@WebServlet(name = "MovieRetrieveServlet", urlPatterns = "/common/MovieRetrieveServlet")
public class MovieRetrieveServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private MovieService movieService;

    public MovieRetrieveServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            Collection<Movie> movieList = movieService.retrieveAll();
            request.setAttribute("movieList", movieList);
            
            String page = request.getParameter("page").trim();
            if (page.equals("index")) {
                request.getRequestDispatcher("/common/index.jsp").forward(request, response);
                return;
            }
            if (page.equals("notvisible")) {
                request.getRequestDispatcher("/admin/notVisiblePage.jsp").forward(request, response);
                return;
            }
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
