/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package control.common;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.util.ArrayList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.mockito.ArgumentCaptor;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import persistence.model.Customer;
import persistence.service.UserService;
import security.UserRole.Role;

/**
 *
 * @author roberto
 */
public class LoginServletTest {
    
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
    private LoginServlet loginServlet;
    
    
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Inizializza i mock
        when(request.getSession()).thenReturn(session);
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
    }
    
    @Test
    public void TC1() throws Exception {
        // Configura i parametri di input della request
        when(request.getParameter("email")).thenReturn("carlo.neri@example.com");
        when(request.getParameter("password")).thenReturn("pass4");

        // Configura il comportamento del mock di UserService
        Customer mockCustomer = new Customer("carlo.neri@example.com", "pass4", "Carlo", "Neri", "Via Roma, 10");
        when(userService.customerLogin("carlo.neri@example.com", "pass4")).thenReturn(mockCustomer);
        when(userService.catalogManagerLogin(anyString(), anyString())).thenReturn(null);
        when(userService.orderManagerLogin(anyString(), anyString())).thenReturn(null);

        // Esegui la servlet
        loginServlet.doPost(request, response);

        // Verifica che il cliente sia stato impostato nella sessione
        verify(session, times(1)).setAttribute("user", mockCustomer);
        verify(session, times(1)).setAttribute("role", Role.CUSTOMER);

        // Verifica che il redirect sia stato chiamato correttamente
        verify(response, times(1)).sendRedirect(request.getContextPath() + "/common/index.jsp");
    }
    
    @Test
    public void TC2_3() throws Exception {
        // Configura i parametri di input della request
        when(request.getParameter("email")).thenReturn("pippo@example.com");
        when(request.getParameter("password")).thenReturn("Topolino456!");

        // Configura il comportamento del mock di UserService
        when(userService.customerLogin("pippo@example.com", "Topolino456!")).thenReturn(null);
        when(userService.catalogManagerLogin(anyString(), anyString())).thenReturn(null);
        when(userService.orderManagerLogin(anyString(), anyString())).thenReturn(null);

        // Esegui la servlet
        loginServlet.doPost(request, response);

        // Verifica che gli errori siano stati impostati
        ArgumentCaptor<ArrayList<String>> errorCaptor = ArgumentCaptor.forClass(ArrayList.class);
        verify(request, times(1)).setAttribute(eq("errors"), errorCaptor.capture());
        ArrayList<String> capturedErrors = errorCaptor.getValue();
        assertNotNull(capturedErrors);
        assertTrue(capturedErrors.contains("Username e/o password errati"));

        // Verifica che il dispatcher abbia inoltrato la richiesta alla pagina di login
        verify(dispatcher, times(1)).forward(request, response);
    }
}
