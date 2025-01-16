/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package control.browse;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import persistence.model.Cart;
import persistence.model.Movie;
import persistence.model.PurchasedMovie;
import persistence.model.RentedMovie;
import persistence.service.CartService;
import persistence.service.UserService;

/**
 *
 * @author roberto
 */
public class UpdateCartServletTest {
    
    @Mock
    private HttpServletRequest request;  // Mock della request
    @Mock
    private HttpServletResponse response;  // Mock della response
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private CartService cartService;
    @InjectMocks
    private UpdateCartServlet servlet;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Inizializza i mock
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }
    
    @Test
    public void TC13() throws Exception {
        // Definiamo i parametri di request per l'azione "aggiungi film"
        when(request.getParameter("action")).thenReturn("add");
        when(request.getParameter("movieid")).thenReturn("1");  // ID del film
        when(request.getParameter("type")).thenReturn("rent");  // Tipo di azione (noleggio)
        when(request.getParameter("days")).thenReturn("5");  // Numero di giorni per il noleggio

        // Crea un oggetto Cart vuoto da aggiungere alla sessione
        Cart cart = new Cart();
        when(request.getSession().getAttribute("cart")).thenReturn(cart);

        // Eseguiamo la servlet
        servlet.doGet(request, response);

        // Verifica che il metodo addRent del servizio CartService sia stato invocato correttamente
        verify(cartService, times(1)).addRent(eq(cart), eq(1), eq(5));

        // Verifica che il redirect sia stato eseguito correttamente
        verify(response, times(1)).sendRedirect(anyString());
    }
    
    @Test
    public void TC14() throws Exception {
        // Definiamo i parametri di request per l'azione "rimuovi film"
        when(request.getParameter("action")).thenReturn("remove");
        when(request.getParameter("movieid")).thenReturn("1");  // ID del film "Inception"

        // Mock del carrello
        Cart cart = spy(new Cart()); // Usa uno spy per testare un oggetto reale

        // Mock del film
        Movie movie = mock(Movie.class);
        when(movie.getId()).thenReturn(1); // L'ID del film è 1

        // Mock del film noleggiato
        RentedMovie rentedMovie = mock(RentedMovie.class);
        when(rentedMovie.getMovie()).thenReturn(movie); // Il film associato

        // Aggiungi il film al carrello
        cart.getRentedMovies().add(rentedMovie);
        
        when(request.getSession().getAttribute("cart")).thenReturn(cart);
        
        // Eseguiamo la servlet
        servlet.doGet(request, response);

        // Verifica che il metodo removeFromCart del servizio Cart sia stato invocato correttamente
        verify(cart, times(1)).removeFromCart(eq(1));

        // Verifica che il film "Inception" sia stato rimosso dal carrello
        assertTrue(cart.isEmpty(), "Il carrello non è vuoto dopo la rimozione del film.");

        // Verifica che il redirect sia stato eseguito correttamente
        verify(response, times(1)).sendRedirect(anyString());
    }


    
}
