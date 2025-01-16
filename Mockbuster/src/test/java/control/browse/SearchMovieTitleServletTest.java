/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package control.browse;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
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
public class SearchMovieTitleServletTest {
    
    @Mock
    private HttpServletRequest request;  // Mock della request
    @Mock
    private HttpServletResponse response;  // Mock della response
    @Mock
    private RequestDispatcher dispatcher;  // Mock del dispatcher
    @Mock
    private HttpSession session;
    @Mock
    private MovieService movieService;
    @InjectMocks
    private SearchMovieTitleServlet servlet;
    
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Inizializza i mock
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
    }
    
    @Test
    public void TC10() throws Exception {
        // Configurazione dei parametri della richiesta
        when(request.getParameter("title")).thenReturn("Inception");

        // Mock del film "Inception" presente nel catalogo
        Movie movie = mock(Movie.class);
        when(movie.getId()).thenReturn(1);
        when(movie.getPosterPath()).thenReturn("/path/to/inception.jpg");
        when(movie.isVisible()).thenReturn(true);

        // Configurazione del servizio per restituire il film
        Collection<Movie> movies = List.of(movie);
        when(movieService.retrieveByTitle("Inception")).thenReturn(movies);

        // Mock dello `OutputStream` per la risposta
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // Esecuzione della servlet
        servlet.doGet(request, response);

        // Verifica della chiamata al servizio
        verify(movieService, times(1)).retrieveByTitle("Inception");

        // Verifica del contenuto della risposta
        writer.flush();
        String jsonResponse = stringWriter.toString();

        // Validazione del JSON
        JSONObject expectedJson = new JSONObject();
        JSONArray moviesArray = new JSONArray();
        JSONObject movieJson = new JSONObject();
        movieJson.put("id", 1);
        movieJson.put("posterpath", "/path/to/inception.jpg");
        moviesArray.put(movieJson);
        expectedJson.put("movies", moviesArray);

        assertEquals(expectedJson.toString(), jsonResponse);
    }
    
    @Test
    public void TC11() throws Exception {
        // Configurazione dei parametri della richiesta
        when(request.getParameter("title")).thenReturn("FilmNonEsistente");

        // Mock del servizio per restituire una lista vuota
        when(movieService.retrieveByTitle("FilmNonEsistente")).thenReturn(Collections.emptyList());

        // Mock dello `OutputStream` per la risposta
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // Esecuzione della servlet
        servlet.doGet(request, response);

        // Verifica della chiamata al servizio
        verify(movieService, times(1)).retrieveByTitle("FilmNonEsistente");

        // Verifica del contenuto della risposta
        writer.flush();
        String jsonResponse = stringWriter.toString();

        // Validazione del JSON
        JSONObject expectedJson = new JSONObject();
        expectedJson.put("movies", new JSONArray()); // JSON vuoto per il catalogo

        assertEquals(expectedJson.toString(), jsonResponse);
    }
    
    @Test
    public void TC12() throws Exception {
        // Configurazione della richiesta
        when(request.getParameter("title")).thenReturn("");

        // Creazione dei film utilizzando i costruttori di default e i setter
        Movie movie1 = new Movie();
        movie1.setId(1);
        movie1.setTitle("Inception");
        movie1.setPosterPath("/path/to/inception.jpg");
        movie1.setVisible(true);

        Movie movie2 = new Movie();
        movie2.setId(2);
        movie2.setTitle("Interstellar");
        movie2.setPosterPath("/path/to/interstellar.jpg");
        movie2.setVisible(true);

        // Lista di film da restituire come catalogo completo
        List<Movie> allMovies = Arrays.asList(movie1, movie2);
        when(movieService.retrieveByTitle("")).thenReturn(allMovies);

        // Mock dello `OutputStream` per la risposta
        StringWriter stringWriter = new StringWriter();
        PrintWriter writer = new PrintWriter(stringWriter);
        when(response.getWriter()).thenReturn(writer);

        // Esecuzione della servlet
        servlet.doGet(request, response);

        // Verifica della chiamata al servizio
        verify(movieService, times(1)).retrieveByTitle("");

        // Verifica del contenuto della risposta
        writer.flush();
        String jsonResponse = stringWriter.toString();

        // Validazione del JSON
        JSONObject expectedJson = new JSONObject();
        JSONArray jsonMovies = new JSONArray();

        // Creazione del JSON atteso
        JSONObject jsonMovie1 = new JSONObject();
        jsonMovie1.put("id", 1);
        jsonMovie1.put("posterpath", "/path/to/inception.jpg");
        jsonMovies.put(jsonMovie1);

        JSONObject jsonMovie2 = new JSONObject();
        jsonMovie2.put("id", 2);
        jsonMovie2.put("posterpath", "/path/to/interstellar.jpg");
        jsonMovies.put(jsonMovie2);

        expectedJson.put("movies", jsonMovies);

        assertEquals(expectedJson.toString(), jsonResponse);
    }    
}
