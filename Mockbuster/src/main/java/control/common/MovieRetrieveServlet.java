package control.common;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import control.exceptions.DAOException;
import jakarta.ejb.EJB;
import jakarta.inject.Inject;
import model.Movie;
import model.dao.MovieDAO;

public class MovieRetrieveServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private MovieDAO movieDAO;

    public MovieRetrieveServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //MovieDAO movieDAO = new MovieDAO((DataSource) getServletContext().getAttribute("DataSource"));
        try {
            String page = request.getParameter("page").trim();
            Collection<Movie> movieList = movieDAO.retrieveAll();
            request.setAttribute("movieList", movieList);

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
