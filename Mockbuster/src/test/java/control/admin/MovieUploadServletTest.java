/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package control.admin;

import control.browse.PlaceOrderServlet;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.Part;
import java.io.File;
import java.io.InputStream;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import persistence.model.Movie;
import persistence.service.MovieService;
import persistence.service.OrderService;

/**
 *
 * @author roberto
 */
public class MovieUploadServletTest {
    
    @Mock
    private HttpServletRequest request;  // Mock della request
    @Mock
    private HttpServletResponse response;  // Mock della response
    @Mock
    private RequestDispatcher dispatcher;  // Mock del dispatcher
    @Mock
    private HttpSession session;
    @Mock
    private ServletContext servletContext;
    @Mock
    private MovieService movieService;
    @InjectMocks
    private MovieUploadServlet servlet;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Inizializza i mock
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getServletContext()).thenReturn(servletContext);
    }
    
    

    /*@Test
    public void TC17() throws Exception {
        // Configurazione dei parametri della richiesta
        when(request.getAttribute("title")).thenReturn("Inception");
        when(request.getAttribute("plot")).thenReturn("trama");
        when(request.getAttribute("duration")).thenReturn("148");
        when(request.getAttribute("year")).thenReturn("2010");
        when(request.getAttribute("availableLicenses")).thenReturn("10");
        when(request.getAttribute("dailyRentalPrice")).thenReturn("2.99");
        when(request.getAttribute("purchasePrice")).thenReturn("12.99");

        // Mock del file Part (poster)
        Part posterPart = mock(Part.class);
        when(request.getPart("poster")).thenReturn(posterPart);
        when(posterPart.getSubmittedFileName()).thenReturn("inception.jpg");

        // Mock del ServletContext per ottenere il percorso di salvataggio
        when(request.getServletContext().getInitParameter("posterSavePath")).thenReturn("/tmp/");

        // Mock per il salvataggio del file
        doNothing().when(posterPart).write(anyString());

        // Mock del servizio MovieService
        doNothing().when(movieService).upload(any(Movie.class));

        // Mock di InputStream per il file
        InputStream mockInputStream = mock(InputStream.class);
        when(posterPart.getInputStream()).thenReturn(mockInputStream);

        // Simuliamo che `exists()` ritorni sempre true
        File mockFile = mock(File.class);
        when(.exists()).thenReturn(true); // Simula che il file esista sempre

        // Mock del comportamento della classe File, per evitare il controllo "real"
        // Evitiamo di creare un'istanza reale di File, e facciamo che il comportamento
        // di exists() sia sempre vero
        doReturn(mockFile).when(request).getAttribute("file");

        // Eseguiamo la servlet
        servlet.doPost(request, response);

        // Verifica che il film sia stato creato correttamente
        ArgumentCaptor<Movie> movieCaptor = ArgumentCaptor.forClass(Movie.class);
        verify(movieService, times(1)).upload(movieCaptor.capture());
        Movie capturedMovie = movieCaptor.getValue();

        assertEquals("Inception", capturedMovie.getTitle());
        assertEquals("trama", capturedMovie.getPlot());
        assertEquals(148, capturedMovie.getDuration());
        assertEquals(2010, capturedMovie.getYear());
        assertEquals(10, capturedMovie.getAvailableLicenses());
        assertEquals(2.99f, capturedMovie.getDailyRentalPrice(), 0.01);
        assertEquals(12.99f, capturedMovie.getPurchasePrice(), 0.01);

        // Verifica che il poster sia stato "salvato"
        verify(posterPart, times(1)).write(contains("/tmp/"));

        // Verifica che la risposta reindirizzi alla pagina principale
        verify(response, times(1)).sendRedirect(eq(request.getContextPath() + "/common/index.jsp"));
    }*/
}






    
    

