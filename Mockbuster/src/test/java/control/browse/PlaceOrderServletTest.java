/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package control.browse;

import jakarta.servlet.FilterChain;
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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import persistence.model.Cart;
import persistence.model.Customer;
import persistence.model.Order;
import persistence.service.OrderService;
import persistence.service.UserService;

/**
 *
 * @author roberto
 */
public class PlaceOrderServletTest {
    
    @Mock
    private HttpServletRequest request;  // Mock della request
    @Mock
    private HttpServletResponse response;  // Mock della response
    @Mock
    private RequestDispatcher dispatcher;  // Mock del dispatcher
    @Mock
    private HttpSession session;
    @Mock
    private OrderService orderService;
    @InjectMocks
    private PlaceOrderServlet servlet;
    
    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Inizializza i mock
        when(request.getRequestDispatcher(anyString())).thenReturn(dispatcher);
        when(request.getSession()).thenReturn(session);
    }
    
    @Test
    public void TC16() throws Exception {
        // Mock del carrello con il film "Inception"
        Cart cart = mock(Cart.class);
        when(cart.isEmpty()).thenReturn(false);

        // Mock dell'utente
        Customer user = mock(Customer.class);
        when(user.getId()).thenReturn(1);

        // Configura la sessione per restituire il carrello e l'utente
        when(session.getAttribute("cart")).thenReturn(cart);
        when(session.getAttribute("user")).thenReturn(user);

        // Mock dell'ordine
        Order order = mock(Order.class);
        when(order.getId()).thenReturn(101);

        // Mock del servizio per completare l'ordine
        when(orderService.placeOrder(user, cart)).thenReturn(order);

        // Mock del contesto della richiesta
        when(request.getContextPath()).thenReturn("/app");

        // Esegui la servlet
        servlet.doGet(request, response);

        // Verifica che il metodo placeOrder sia stato chiamato
        verify(orderService, times(1)).placeOrder(user, cart);

        // Verifica che il redirect sia stato eseguito correttamente
        verify(response, times(1)).sendRedirect("/app/browse/GetOrdersServlet?userid=1&orderid=101");
    }

    
}
