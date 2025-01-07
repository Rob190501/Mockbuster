package control.admin;

import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import control.exceptions.DAOException;
import jakarta.inject.Inject;
import model.Movie;
import model.dao.MovieDAO;

@WebServlet("/MovieUpdateServlet")
public class MovieUpdateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private MovieDAO movieDAO;

    public MovieUpdateServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Integer movieid = Integer.parseInt(request.getParameter("movieid").trim());
        try {
            Movie movie = movieDAO.retrieveByID(movieid);

            if (movie == null) {
                response.sendRedirect(request.getContextPath() + "/common/index.jsp");
                return;
            }

            request.setAttribute("movie", movie);
            request.getRequestDispatcher("/admin/movieUpdate.jsp").forward(request, response);
        } catch (DAOException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getAttribute("errors") == null) {
            request.setCharacterEncoding("UTF-8");

            Integer id = Integer.parseInt(request.getParameter("movieid").trim());
            String title = request.getParameter("title").trim();
            String plot = request.getParameter("plot").trim();
            Integer duration = Integer.parseInt(request.getParameter("duration").trim());
            Integer year = Integer.parseInt(request.getParameter("year").trim());
            Integer availableLicenses = Integer.parseInt(request.getParameter("availableLicenses").trim());
            Float dailyRentalPrice = Float.parseFloat(request.getParameter("dailyRentalPrice").trim());
            Float purchasePrice = Float.parseFloat(request.getParameter("purchasePrice").trim());
            Boolean isVisible = request.getParameter("isVisible") != null;

            Movie tempMovie = new Movie(id, title, plot, duration, year, availableLicenses, dailyRentalPrice, purchasePrice, isVisible);

            try {
                movieDAO.update(tempMovie);
                response.sendRedirect(request.getContextPath() + "/browse/MoviePageServlet?id=" + tempMovie.getId());
                return;
            } catch (DAOException e) {
                e.printStackTrace();
                throw new ServletException(e);
            }
        }
        doGet(request, response);
    }

}
