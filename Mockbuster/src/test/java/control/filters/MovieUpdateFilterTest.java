/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package control.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.FilterConfig;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import persistence.service.UserService;

/**
 *
 * @author roberto
 */
public class MovieUpdateFilterTest {
    
    @Mock
    private HttpServletRequest request;  // Mock della request
    @Mock
    private HttpServletResponse response;  // Mock della response
    @Mock
    private RequestDispatcher dispatcher;  // Mock del dispatcher
    @Mock
    private HttpSession session;
    @Mock
    private FilterChain chain;  // Mock della FilterChain
    @InjectMocks
    private MovieUpdateFilter filter;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Inizializza i mock
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
    }
    
    @Test
    public void TC18() throws Exception {
        // Parametri della richiesta (film Inception con dati invalidi)
        when(request.getMethod()).thenReturn("POST");
        when(request.getParameter("movieid")).thenReturn("1");  // ID valido
        when(request.getParameter("title")).thenReturn("");  // Titolo non valido (stringa vuota)
        when(request.getParameter("plot")).thenReturn("Plot valido.");
        when(request.getParameter("duration")).thenReturn("-10");  // Durata non valida (negativa)
        when(request.getParameter("year")).thenReturn("900");  // Anno valido ma fuori limite
        when(request.getParameter("availableLicenses")).thenReturn("10");
        when(request.getParameter("dailyRentalPrice")).thenReturn("-2");  // Prezzo negativo
        when(request.getParameter("purchasePrice")).thenReturn("10");

        // Esegui il filtro
        filter.doFilter(request, response, chain);

        // Verifica che l'inoltro della richiesta sia stato effettuato (se i dati sono validi)
        verify(chain, never()).doFilter(request, response);  // La richiesta non dovrebbe passare se i dati sono invalidi

        // Verifica che la risposta contenga l'errore relativo ai dati invalidi
        // Simuliamo l'attributo "errors" per il forwarding
        verify(request).setAttribute(eq("errors"), anyList());

        // Verifica che venga effettuato il forwarding alla pagina di errore
        verify(request).getRequestDispatcher("/admin/MovieUpdateServlet");
    }
    
    
    
}
