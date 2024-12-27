package control.admin;

import java.io.File;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import javax.sql.DataSource;

import control.exceptions.DAOException;
import model.Movie;
import model.dao.MovieDAO;

@MultipartConfig(
	fileSizeThreshold = 1024 * 1024 * 2, // 2MB
	maxFileSize = 1024 * 1024 * 10,      // 10MB
	maxRequestSize = 1024 * 1024 * 50    // 50MB
)
public class MovieUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static String IMAGE_DIR = "images/posters";

    public MovieUploadServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		request.getRequestDispatcher("/errors/error405.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String title = ((String)request.getAttribute("title")).trim();
		String plot = ((String)request.getAttribute("plot")).trim();
		Integer duration = Integer.parseInt(((String)request.getAttribute("duration")).trim());
		Integer year = Integer.parseInt(((String)request.getAttribute("year")).trim());
		Integer availableLicenses = Integer.parseInt(((String)request.getAttribute("availableLicenses")).trim());
		Float dailyRentalPrice = Float.parseFloat(((String)request.getAttribute("dailyRentalPrice")).trim());
		Float purchasePrice = Float.parseFloat(((String)request.getAttribute("purchasePrice")).trim());
		Part poster = request.getPart("poster");
		String posterName = poster.getSubmittedFileName();
		
		Movie movie = new Movie(title, plot, duration, year, availableLicenses, dailyRentalPrice, purchasePrice, posterName);
		String savePath = request.getServletContext().getRealPath("") + IMAGE_DIR;
		
		MovieDAO movieDAO = new MovieDAO((DataSource)getServletContext().getAttribute("DataSource"));
		
		try {
			movieDAO.save(movie);
			poster.write(savePath + File.separator + posterName);
			response.sendRedirect(request.getContextPath() + "/common/index.jsp");
		} catch (DAOException | IOException e) {
			e.printStackTrace();
			throw new ServletException(e);
		}
	}
}
