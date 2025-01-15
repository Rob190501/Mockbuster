package control.browse;

import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.json.JSONArray;
import org.json.JSONObject;
import control.exceptions.DAOException;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import java.util.Collection;
import persistence.model.Movie;
import persistence.service.MovieService;



//@WebServlet(name = "SearchMovieTitleServlet", urlPatterns = {"/browse/SearchMovieTitleServlet"})
public class SearchMovieTitleServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private MovieService movieService;

    public SearchMovieTitleServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = request.getParameter("title").trim();
        if (title == null) {
            title = "";
        }

        response.setContentType("application/json");
        PrintWriter out = response.getWriter();
        
        try {
            Collection<Movie> movies = movieService.retrieveByTitle(title);
            
            JSONArray jsonMovies = new JSONArray();
            JSONObject jsonResponse = new JSONObject();

            for (Movie movie : movies) {
                if (movie.isVisible()) {
                    JSONObject jsonMovie = new JSONObject();
                    jsonMovie.put("id", movie.getId());
                    jsonMovie.put("posterpath", movie.getPosterPath());
                    jsonMovies.put(jsonMovie);
                }
            }

            jsonResponse.put("movies", jsonMovies);
            out.print(jsonResponse.toString());
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }
}
