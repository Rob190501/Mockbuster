/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package control.common;

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
import persistence.model.Cart;
import persistence.model.Customer;
import persistence.service.UserService;
import security.UserRole;
import security.UserRole.Role;

/**
 *
 * @author roberto
 */
public class SignupServletTest {
    
    @Mock
    private HttpServletRequest request;  // Mock della request
    @Mock
    private HttpServletResponse response;  // Mock della response
    @Mock
    private HttpSession session;
    @Mock
    private RequestDispatcher dispatcher;
    @Mock
    private UserService userService;
    @InjectMocks
    private SignupServlet signupServlet;
    
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Inizializza i mock
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }
    
    
    @Test
    public void TC5() throws Exception {
        // Definiamo i parametri di request che rappresentano i dati del test
        when(request.getParameter("email")).thenReturn("nuovo@example.com");
        when(request.getParameter("password")).thenReturn("Pluto123");
        when(request.getParameter("firstName")).thenReturn("Mario");
        when(request.getParameter("lastName")).thenReturn("Bianchi");
        when(request.getParameter("billingAddress")).thenReturn("Via Roma 12 40100 Bologna");

        // Esegui la servlet
        signupServlet.doPost(request, response);
        
        // Verifica che il servizio signup sia stato chiamato
        verify(userService, times(1)).signup(any(Customer.class));

        // Verifica che l'utente sia stato registrato nella sessione
        verify(session, times(1)).setAttribute(eq("user"), any(Customer.class));
        verify(session, times(1)).setAttribute(eq("role"), any(Role.class));
        verify(session, times(1)).setAttribute(eq("cart"), any(Cart.class));

        // Verifica che il redirect sia stato chiamato correttamente
        verify(response, times(1)).sendRedirect(anyString());
    }
    
}
