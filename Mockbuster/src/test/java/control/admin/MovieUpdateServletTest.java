/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package control.admin;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import persistence.model.Movie;
import persistence.service.MovieService;

/**
 *
 * @author roberto
 */
public class MovieUpdateServletTest {
    
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
    private MovieUpdateServlet servlet;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Inizializza i mock
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
        when(request.getServletContext()).thenReturn(servletContext);
    }

    @Test
    public void TC19() throws Exception {
        // Parametri della richiesta per rimuovere il film (isVisible = false)
        when(request.getParameter("movieid")).thenReturn("1");
        when(request.getParameter("title")).thenReturn("Inception");
        when(request.getParameter("plot")).thenReturn("Trama");
        when(request.getParameter("duration")).thenReturn("148");
        when(request.getParameter("year")).thenReturn("2010");
        when(request.getParameter("availableLicenses")).thenReturn("10");
        when(request.getParameter("dailyRentalPrice")).thenReturn("2.99");
        when(request.getParameter("purchasePrice")).thenReturn("12.99");
        when(request.getParameter("isVisible")).thenReturn(null); // Imposta isVisible su false

        // Simulazione della risposta del servizio di aggiornamento
        doNothing().when(movieService).update(any(Movie.class));

        // Esegui il metodo doPost della servlet
        servlet.doPost(request, response);

        // Verifica che il film sia stato aggiornato con isVisible = false
        ArgumentCaptor<Movie> movieCaptor = ArgumentCaptor.forClass(Movie.class);
        verify(movieService, times(1)).update(movieCaptor.capture());
        Movie capturedMovie = movieCaptor.getValue();
        assertFalse(capturedMovie.isVisible(), "Il film dovrebbe essere invisibile");

        // Verifica che la risposta reindirizzi alla pagina corretta
        verify(response, times(1)).sendRedirect(eq(request.getContextPath() + "/browse/MoviePageServlet?id=1"));
    }


    
}
