/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package control.filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;
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
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import persistence.model.Customer;
import persistence.service.UserService;

/**
 *
 * @author roberto
 */
public class SignupFormFilterTest {
    
    @Mock
    private HttpServletRequest request;  // Mock della request
    @Mock
    private HttpServletResponse response;  // Mock della response
    @Mock
    private RequestDispatcher dispatcher;  // Mock del dispatcher
    @Mock
    private FilterChain chain;  // Mock della FilterChain
    @Mock
    private UserService userService;
    @InjectMocks
    private SignupFormFilter filter;  // Filtro che stiamo testando
    
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Inizializza i mock
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }

    @Test
    public void TC4() throws Exception {
        // Definiamo i parametri di request che rappresentano i dati del test
        when(request.getParameter("email")).thenReturn("pippo@example.com");
        when(request.getParameter("password")).thenReturn("Pluto123");
        when(request.getParameter("firstName")).thenReturn("Pippo");
        when(request.getParameter("lastName")).thenReturn("Rossi");
        when(request.getParameter("billingAddress")).thenReturn("Via Roma 12 40100 Bologna");
        
        // Simula la condizione in cui l'email è già registrata
        when(userService.checkEmailAvailability("pippo@example.com")).thenReturn(false);
        
        // Esegui il filtro
        filter.doFilter(request, response, chain);

        // Verifica che gli errori siano stati impostati sulla request
        verify(request, times(1)).setAttribute(eq("errors"), any());
        
        // Verifica che il forward sia stato chiamato
        verify(dispatcher, times(1)).forward(request, response);
        
        // Cattura la lista degli errori
        ArgumentCaptor<ArrayList> errorsCaptor = ArgumentCaptor.forClass(ArrayList.class);
        verify(request).setAttribute(eq("errors"), errorsCaptor.capture());
        
        // Verifica che l'errore 'Email già registrata' sia presente nella lista
        assertTrue(errorsCaptor.getValue().contains("Email gia' registrata"), "Errore non trovato: Email già registrata");
    }
    
    
    @Test
    public void TC6() throws Exception {
        // Definiamo i parametri di request che rappresentano i dati del test
        when(request.getParameter("email")).thenReturn("errata");
        when(request.getParameter("password")).thenReturn("Pluto123");
        when(request.getParameter("firstName")).thenReturn("Luigi");
        when(request.getParameter("lastName")).thenReturn("Verdi");
        when(request.getParameter("billingAddress")).thenReturn("Via Roma 12 40100 Bologna");
        
        ArgumentCaptor<ArrayList<String>> errorsCaptor = ArgumentCaptor.forClass(ArrayList.class);
        
        // Eseguiamo il filtro
        filter.doFilter(request, response, chain);
        
        // Verifica che i parametri di errore siano stati settati
        verify(request, times(1)).setAttribute(eq("errors"), errorsCaptor.capture());
        verify(dispatcher, times(1)).forward(request, response);
        assertTrue(errorsCaptor.getValue().contains("Email non valida"));
    }
    
    @Test
    public void TC7() throws Exception {
        // Definiamo i parametri di request che rappresentano i dati del test
        when(request.getParameter("email")).thenReturn("nuovo@example.com");
        when(request.getParameter("password")).thenReturn("aa");  // La password è troppo breve
        when(request.getParameter("firstName")).thenReturn("Giulia");
        when(request.getParameter("lastName")).thenReturn("Ferrari");
        when(request.getParameter("billingAddress")).thenReturn("Via Roma 12 40100 Bologna");

        // Catturiamo l'oggetto errors impostato nella request
        ArgumentCaptor<ArrayList<String>> errorsCaptor = ArgumentCaptor.forClass(ArrayList.class);

        // Eseguiamo il filtro
        filter.doFilter(request, response, chain);

        // Verifica che i parametri di errore siano stati settati
        verify(request, times(1)).setAttribute(eq("errors"), errorsCaptor.capture());
        verify(dispatcher, times(1)).forward(request, response);

        // Verifica che il messaggio di errore "Password non valida" sia presente nella lista degli errori
        assertTrue(errorsCaptor.getValue().contains("Password non valida"));
    }
    
    @Test
    public void TC8() throws Exception {
        // Definiamo i parametri di request che rappresentano i dati del test
        when(request.getParameter("email")).thenReturn("nuovo@example.com");
        when(request.getParameter("password")).thenReturn("Pluto123");
        when(request.getParameter("firstName")).thenReturn("");  // Nome vuoto
        when(request.getParameter("lastName")).thenReturn("Verdi");
        when(request.getParameter("billingAddress")).thenReturn("Via Roma 12 40100 Bologna");

        // Catturiamo l'oggetto errors impostato nella request
        ArgumentCaptor<ArrayList<String>> errorsCaptor = ArgumentCaptor.forClass(ArrayList.class);

        // Eseguiamo il filtro
        filter.doFilter(request, response, chain);

        // Verifica che i parametri di errore siano stati settati
        verify(request, times(1)).setAttribute(eq("errors"), errorsCaptor.capture());
        verify(dispatcher, times(1)).forward(request, response);

        // Verifica che il messaggio di errore "Nome non valido" sia presente nella lista degli errori
        assertTrue(errorsCaptor.getValue().contains("Nome non valido"));
    }
    
    @Test
    public void TC9() throws Exception {
        // Definiamo i parametri di request che rappresentano i dati del test
        when(request.getParameter("email")).thenReturn("nuovo@example.com");
        when(request.getParameter("password")).thenReturn("Pluto123");
        when(request.getParameter("firstName")).thenReturn("Anna");
        when(request.getParameter("lastName")).thenReturn("Bianchi");
        when(request.getParameter("billingAddress")).thenReturn("");  // Indirizzo vuoto

        // Catturiamo l'oggetto errors impostato nella request
        ArgumentCaptor<ArrayList<String>> errorsCaptor = ArgumentCaptor.forClass(ArrayList.class);

        // Eseguiamo il filtro
        filter.doFilter(request, response, chain);

        // Verifica che i parametri di errore siano stati settati
        verify(request, times(1)).setAttribute(eq("errors"), errorsCaptor.capture());
        verify(dispatcher, times(1)).forward(request, response);

        // Verifica che il messaggio di errore "Indirizzo non valido" sia presente nella lista degli errori
        assertTrue(errorsCaptor.getValue().contains("Indirizzo non valido"));
    }
    
}
