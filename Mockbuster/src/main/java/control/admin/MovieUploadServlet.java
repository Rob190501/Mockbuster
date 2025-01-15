package control.admin;

import java.io.File;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.Part;
import control.exceptions.DAOException;
import jakarta.inject.Inject;
import jakarta.servlet.annotation.WebServlet;
import java.util.UUID;
import persistence.model.Movie;
import persistence.service.MovieService;



//@WebServlet(name = "MovieUploadServlet", urlPatterns = "/admin/MovieUploadServlet")
@MultipartConfig(fileSizeThreshold=1024*1024*10,    // 10 MB 
                 maxFileSize=1024*1024*50,          // 50 MB
                 maxRequestSize=1024*1024*100,      // 100 MB
                 location="/")
public class MovieUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    
    @Inject
    private MovieService movieService;

    public MovieUploadServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setStatus(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
        request.getRequestDispatcher("/errors/error405.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String title = ((String) request.getAttribute("title")).trim();
        String plot = ((String) request.getAttribute("plot")).trim();
        Integer duration = Integer.parseInt(((String) request.getAttribute("duration")).trim());
        Integer year = Integer.parseInt(((String) request.getAttribute("year")).trim());
        Integer availableLicenses = Integer.parseInt(((String) request.getAttribute("availableLicenses")).trim());
        Float dailyRentalPrice = Float.parseFloat(((String) request.getAttribute("dailyRentalPrice")).trim());
        Float purchasePrice = Float.parseFloat(((String) request.getAttribute("purchasePrice")).trim());
        Part poster = request.getPart("poster");
        String posterName = poster.getSubmittedFileName();
        
        String randomPosterName = generateRandomFilename(posterName);

        Movie movie = new Movie(title, plot, duration, year, availableLicenses, dailyRentalPrice, purchasePrice, randomPosterName);
        String savePath = request.getServletContext().getInitParameter("posterSavePath");
        
        try {
            poster.write(savePath + randomPosterName);
            movieService.upload(movie);
            
            File posterFile = new File(savePath + randomPosterName);
            
            if(posterFile.exists()) {
                response.sendRedirect(request.getContextPath() + "/common/index.jsp");
            }
            else {
                throw new IOException("Impossibile salvare il poster");
            }
        } catch (DAOException | IOException e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
        
    }
    
    private String generateRandomFilename(String originalFileName) {
        // Ottieni l'estensione del file
        int dotIndex = originalFileName.lastIndexOf(".");
        String extension = originalFileName.substring(dotIndex);
        
        // Crea un nome randomizzato usando UUID
        String randomName = UUID.randomUUID().toString();
        
        // Combina il nome randomico con l'estensione del file
        return randomName + extension;
    }
}
